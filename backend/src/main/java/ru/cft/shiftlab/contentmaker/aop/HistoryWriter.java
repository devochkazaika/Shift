package ru.cft.shiftlab.contentmaker.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
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

    @Around("@annotation(ru.cft.shiftlab.contentmaker.aop.History)")
    public Object aroundAddingAdvice(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        StoryPresentation result = null;
        try {
            result = (StoryPresentation) joinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
        HistoryEntity history = new HistoryEntity();
                history.setBankId(result.getBankId());
                history.setPlatform(result.getPlatform());
                history.setOperationType(HistoryEntity.OperationType.Change);
                history.setDay(LocalDate.now());
                history.setTime(LocalTime.now());
                history.setComponentId(result.getId());
                history.setUserName(keycloak.getUserName());
        historyRepository.save(history);
        return result;
    }
}
