import StoryCard from "./StoryCard";
import "./StoryPanelStyle.css";
import Button from "./../../ui/Button/index";
import { deleteStory } from "./../../../api/stories";
import { useState, useEffect, React } from "react";
import ShowStory from "../ShowStory";

const StoryPanel = ({ storyArray, platform }) => {
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
        setStories((prevStories) =>
          prevStories.filter((item) => item.id !== story.id),
        );
      }
    } catch (error) {
      console.error("Ошибка при удалении истории", error);
    }
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
                <div style={{width: "70%", paddingRight: "30px"}}>
                  <StoryCard
                    key={index}
                    story={story}
                    storyIndex={index}
                    platform={platform}
                  />
                </div>
                <div style={{width: "30%", paddingRight: "4px"}}>
                  <h2>Примерный вид</h2>
                  <ShowStory stories={story}/>
                </div>
              </div>
            </details>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default StoryPanel;
