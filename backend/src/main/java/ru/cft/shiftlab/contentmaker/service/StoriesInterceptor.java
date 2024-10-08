package ru.cft.shiftlab.contentmaker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.cft.shiftlab.contentmaker.entity.HistoryEntity;
import ru.cft.shiftlab.contentmaker.repository.HistoryRepository;
import ru.cft.shiftlab.contentmaker.repository.StoryPresentationRepository;

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
    Map<Integer, HistoryEntity.Status> statusToBd = new HashMap<>(){{
        put(200, HistoryEntity.Status.SUCCESSFUL);
        put(400, HistoryEntity.Status.BAD);
        put(500, HistoryEntity.Status.SERVER_ERROR);
    }};
    @Autowired
    HistoryRepository historyRepository;
    @Autowired
    StoryPresentationRepository storyPresentationRepository;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StoriesInterceptor.class);
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        String requestURI = request.getRequestURI();
//        HistoryEntity history = new HistoryEntity();
//        history.setTime(LocalTime.now());
//        history.setDay(LocalDate.now());
//        Set<String> path = Arrays.stream(requestURI.split("/")).collect(Collectors.toSet());
//        try{
//            if (path.contains("get")) return;
//            else if (path.contains("stories")){
//                history.setComponentType(HistoryEntity.ComponentType.STORIES);
//                if (path.contains("add")){
//                    history.setOperationType(HistoryEntity.OperationType.Create);
//                    var story = storyPresentationRepository.findTopByOrderByIdDesc();
//                    history.setComponentId(story.getId());
//                    history.setBankId(story.getBankId());
//                    history.setPlatform(story.getPlatform());
//                }
//                else if (path.contains("delete")){
//                    history.setOperationType(HistoryEntity.OperationType.Delete);
//                    if (request.getParameter("id") != null) {
//                        history.setComponentId(Long.parseLong(request.getParameter("id")));
//                    }
//                }
//                else if (path.contains("change")){
//                    history.setOperationType(HistoryEntity.OperationType.Update);
//                    history.setComponentId(Long.parseLong(request.getParameter("id")));
//                }
//                else return;
//                if (request.getParameter("bankId") != null){
//                    history.setBankId(request.getParameter("bankId"));
//                }
//                if (request.getParameter("platform") != null){
//                    history.setPlatform(request.getParameter("platform"));
//                }
//                history.setStatus(statusToBd.get(response.getStatus() / 100 * 100));
//                history.setUserName(request.getRemoteUser());
//                historyRepository.save(history);
//            }
//        }
//        catch (Exception e){
//            log.info("Операция не сохранилась - " + e.getMessage());;
//        }
    }

    private boolean rollBackStoryRequest(){
        return true;
    }

    public boolean rollBackRequest(Long idOperatin, HistoryEntity.ComponentType contentType){
        switch (contentType){
            case STORIES:

                break;
        }
//        historyRepository.
        return true;
    }
}
