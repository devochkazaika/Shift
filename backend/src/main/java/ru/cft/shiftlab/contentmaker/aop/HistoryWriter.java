package ru.cft.shiftlab.contentmaker.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.cft.shiftlab.contentmaker.entity.HistoryEntity;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentationFrames;
import ru.cft.shiftlab.contentmaker.repository.HistoryRepository;
import ru.cft.shiftlab.contentmaker.util.keycloak.KeyCloak;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class HistoryWriter {
    private final HistoryRepository historyRepository;
    private final KeyCloak keycloak;

    private StoryPresentation resultStories(ProceedingJoinPoint joinPoint, HistoryEntity history) {
        StoryPresentation result = null;
        history.setComponentType(HistoryEntity.ComponentType.STORIES);
        try {
            result = (StoryPresentation) joinPoint.proceed();
            history.setBankId(result.getBankId());
            history.setPlatform(result.getPlatform());
            history.setStatus(HistoryEntity.Status.SUCCESSFUL);
            history.setComponentId(result.getId());
            history.setRollBackAble(true);
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            history.setStatus(HistoryEntity.Status.BAD);
            history.setRollBackAble(false);
        }
        return result;
    }

    private void resultDeleteStories(ProceedingJoinPoint joinPoint, HistoryEntity history, Object[] arguments) {
        ResponseEntity<?> result;
        history.setComponentType(HistoryEntity.ComponentType.STORIES);
        try {
            result = (ResponseEntity<?>) joinPoint.proceed();
            history.setBankId(arguments[0].toString());
            history.setPlatform(arguments[1].toString());
            history.setStatus(HistoryEntity.Status.getStatus(result.getStatusCodeValue()));
            history.setComponentId((Long) arguments[2]);
            history.setStatus(HistoryEntity.Status.SUCCESSFUL);
            // Пока что false потому что логика восстановления уже есть
            history.setRollBackAble(false);

        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            history.setStatus(HistoryEntity.Status.BAD);
        }
    }

    private void historyStoryChanging(ProceedingJoinPoint joinPoint, HistoryEntity history) throws Throwable {
        history.setOperationType(HistoryEntity.OperationType.Change);
        var story = resultStories(joinPoint, history);
        // Запись с измененной историей
        history.setSecondComponentId(story.getId());
    }
    private void historyStorySaving(ProceedingJoinPoint joinPoint, HistoryEntity history) throws Throwable {
        history.setOperationType(HistoryEntity.OperationType.Create);
        resultStories(joinPoint, history);
    }
    private void historyStoryApproving(ProceedingJoinPoint joinPoint, HistoryEntity history) throws Throwable {
        history.setOperationType(HistoryEntity.OperationType.Update);
        resultStories(joinPoint, history);
    }
    private void historyStoryDeleting(ProceedingJoinPoint joinPoint, HistoryEntity history) throws Throwable {
        history.setOperationType(HistoryEntity.OperationType.Delete);
        Object[] arguments = joinPoint.getArgs();
        resultDeleteStories(joinPoint, history, arguments);
    }


    private StoryPresentationFrames historyFrameSave(ProceedingJoinPoint joinPoint, HistoryEntity history) throws Throwable {
        history.setOperationType(HistoryEntity.OperationType.Create);
        history.setComponentType(HistoryEntity.ComponentType.FRAMES);
        Object[] arguments = joinPoint.getArgs();
        StoryPresentationFrames result = null;
        try {
            result = (StoryPresentationFrames) joinPoint.proceed();
            history.setBankId((String) arguments[2]);
            history.setPlatform((String) arguments[3]);
            history.setComponentId((Long) arguments[4]);
            history.setStatus(HistoryEntity.Status.SUCCESSFUL);
            history.setAdditionalUuid(result.getId());
            history.setRollBackAble(true);
        } catch (Throwable e) {
//            log.error(e.getMessage(), e);
            history.setRollBackAble(false);
            history.setStatus(HistoryEntity.Status.BAD);
        }
        return result;
    }

    @Around("@annotation(ru.cft.shiftlab.contentmaker.aop.History)")
    public Object aroundAddingAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Aspect triggered for method: " + joinPoint.getSignature().getName());
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        HistoryEntity history = new HistoryEntity();
        Object result = null;
        switch (methodSignature.getName()){
            case "changeStory":
                historyStoryChanging(joinPoint, history);
                break;
            case "saveStory":
                historyStorySaving(joinPoint, history);
                break;
            case "approveStory":
                historyStoryApproving(joinPoint, history);
                break;
            case "deleteService":
                historyStoryDeleting(joinPoint, history);
                break;
            case "addFrame":
                result = historyFrameSave(joinPoint, history);
                break;

        }
        history.setUserName(keycloak.getUserName());
        history.setDay(LocalDate.now());
        history.setTime(LocalTime.now());
        historyRepository.save(history);
        return result;
    }
}
