package ru.cft.shiftlab.contentmaker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ru.cft.shiftlab.contentmaker.entity.History;
import ru.cft.shiftlab.contentmaker.repository.HistoryRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class StoriesInterceptor implements HandlerInterceptor {
    Map<Integer, History.Status> statusToBd = new HashMap<>(){{
        put(200, History.Status.SUCCESSFUL);
        put(500, History.Status.SERVER_ERROR);
    }};
    @Autowired
    HistoryRepository historyRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/stories")) {
            System.out.println("Request URI: " + requestURI);
            System.out.println("Request Method: " + response);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/stories")){
            historyRepository.save(History.builder()
                            .status(statusToBd.get(response.getStatus()))
                            .time(LocalDate.now())
                            .userName(request.getRemoteUser())
                    .build());
        }
    }
}
