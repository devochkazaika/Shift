package ru.cft.shiftlab.contentmaker.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.cft.shiftlab.contentmaker.entity.HistoryEntity;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;
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

    private void resultCreate(ProceedingJoinPoint joinPoint, HistoryEntity history) {
        StoryPresentation result;
        try {
            result = (StoryPresentation) joinPoint.proceed();
            history.setBankId(result.getBankId());
            history.setPlatform(result.getPlatform());
            history.setStatus(HistoryEntity.Status.SUCCESSFUL);
            history.setComponentId(result.getId());
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            history.setStatus(HistoryEntity.Status.BAD);
        }
    }

    private void historyStoryChanging(ProceedingJoinPoint joinPoint, HistoryEntity history) throws Throwable {
        StoryPresentation result = null;
        history.setComponentType(HistoryEntity.ComponentType.STORIES);
        history.setOperationType(HistoryEntity.OperationType.Change);
        resultCreate(joinPoint, history);
    }
    private void historyStorySaving(ProceedingJoinPoint joinPoint, HistoryEntity history) throws Throwable {
        StoryPresentation result = null;
        history.setComponentType(HistoryEntity.ComponentType.STORIES);
        history.setOperationType(HistoryEntity.OperationType.Create);
        resultCreate(joinPoint, history);
    }

    @Around("@annotation(ru.cft.shiftlab.contentmaker.aop.History)")
    public Object aroundAddingAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Aspect triggered for method: " + joinPoint.getSignature().getName());
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        HistoryEntity history = new HistoryEntity();
        switch (methodSignature.getName()){
            case "changeStory":
                historyStoryChanging(joinPoint, history);
                break;
            case "saveFiles":
                historyStorySaving(joinPoint, history);
                break;
        }
        history.setUserName(keycloak.getUserName());
        history.setDay(LocalDate.now());
        history.setTime(LocalTime.now());
        historyRepository.save(history);
        return null;
    }
}
