import StoryCard from "./StoryCard";
import "./StoryPanelStyle.css";
import Button from "./../../ui/Button/index";
import { deleteStory } from "./../../../api/stories";
import { useState, useEffect, React } from "react";
import ShowStory from "../ShowStory";

const StoryPanel = ({showStory, storyArray, platform }) => {
  const [stories, setStories] = useState(storyArray);
  //Для обновления после удаления
  useEffect(() => {
    if (storyArray) {
      setStories(storyArray);
    }
  }, [storyArray]);
  //Для удаления истории
  const handleOnSubmit = async (story, platform) => {
    try {
      const success = await deleteStory(story, platform);
      if (success) {
        setStories((prevStories) => {
          const updatedStories = prevStories.filter((item) => item.id !== story.id);
          return updatedStories;
        });
      }
    } catch (error) {
      console.error("Ошибка при удалении истории", error);
    }
  };

  const handleUpdateStory = (value, storyIndex, frameId, field) => {
    // Создаем копию массива stories
    let newStories = [...stories];
  
    // Находим нужный storyFrames по индексу storyIndex
    newStories[storyIndex].storyFrames = newStories[storyIndex].storyFrames.map(frame => {
      if (frame.id === frameId) {
        return {
          ...frame,
          [field]: value
        };
      }
      return frame;
    });
  
    // Обновляем состояние
    setStories(newStories);
  };
  return (
    <div>
      <ul className="stories">
          <h2>{platform}</h2>
        {stories.map((story, index) => (
          <li className="listFrame" key={index}>
            <details>
              <summary>
                <p>{story.previewTitle}</p>
                <div>
                  <div>
                    <Button
                      text="Удалить"
                      type="button"
                      color="red"
                      handleOnClick={() => handleOnSubmit(story, platform)}
                    />
                  </div>
                </div>
              </summary>
              <div style={{display: "flex"}}>
                {(showStory) ? 
                  <>
                  <div style={{width: "70%", paddingRight: "30px"}}>
                    <StoryCard
                      key={index}
                      story={story}
                      storyIndex={index}
                      platform={platform}
                      changeStory={handleUpdateStory}
                    />
                  </div>
                  <div style={{width: "30%", paddingRight: "4px"}}>
                    <ShowStory stories={storyArray[index]}/>
                  </div>
                  </>
                : <>
                  <div style={{width: "100%", paddingRight: "30px"}}>
                    <StoryCard
                      key={index}
                      story={story}
                      storyIndex={index}
                      platform={platform}
                    />
                  </div>
                  </>}
              </div>
            </details>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default StoryPanel;
