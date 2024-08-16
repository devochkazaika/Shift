package ru.cft.shiftlab.contentmaker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.cft.shiftlab.contentmaker.entity.History;
import ru.cft.shiftlab.contentmaker.repository.HistoryRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StoriesInterceptor implements HandlerInterceptor {
    Map<Integer, History.Status> statusToBd = new HashMap<>(){{
        put(200, History.Status.SUCCESSFUL);
        put(400, History.Status.BAD);
        put(500, History.Status.SERVER_ERROR);
    }};
    @Autowired
    HistoryRepository historyRepository;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        History history = new History();
        history.setTime(LocalTime.now());
        history.setDay(LocalDate.now());
        Set<String> path = Arrays.stream(requestURI.split("/")).collect(Collectors.toSet());
        if (path.contains("get")) return;
        else if (path.contains("stories")){
            history.setComponentType(History.ComponentType.STORIES);
            if (path.contains("add")){
                history.setOperationType(History.OperationType.Create);
                history.setComponentId(historyRepository.getLastHistory().getId());
            }
            else if (path.contains("delete")){
                history.setOperationType(History.OperationType.Delete);
                history.setComponentId(Long.parseLong(request.getParameter("id")));
            }
            else if (path.contains("change")){
                history.setOperationType(History.OperationType.Update);
                history.setComponentId(Long.parseLong(request.getParameter("id")));
            }
            if (request.getParameter("bankId") != null){
                history.setBankId(request.getParameter("bankId"));
            }
            if (request.getParameter("platform") != null){
                history.setPlatform(request.getParameter("platform"));
            }
            history.setStatus(statusToBd.get(response.getStatus() / 100 * 100));
            history.setUserName(request.getRemoteUser());
            historyRepository.save(history);
        }
    }
}
