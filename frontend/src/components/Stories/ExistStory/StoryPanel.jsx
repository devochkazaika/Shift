import StoryCard from "./StoryCard";
import styles from "../../../styles/StoryPanelStyle.module.scss";
import Button from "./../../ui/Button/index";
import { deleteStory } from "./../../../api/stories";
import React, { useState, useEffect } from "react";
import ShowStory from "../ShowStory";


const StoryPanel = ({ showStory, storyArray, platform }) => {
  const [stories, setStories] = useState(storyArray);

  // Для обновления после удаления
  useEffect(() => {
    if (storyArray) {
      setStories(storyArray);
    }
  }, [storyArray]);

  // Для удаления истории
  const handleOnSubmit = async (story, platform) => {
    try {
      const success = await deleteStory(story, platform);
      if (success) {
        setStories((prevStories) => prevStories.filter((item) => item.id !== story.id));
      }
    } catch (error) {
      console.error("Ошибка при удалении истории", error);
    }
  };

  const handleUpdateStory = (value, storyIndex, frameId, field) => {
    let newStories = [...stories];

    newStories[storyIndex].storyFrames = newStories[storyIndex].storyFrames.map((frame) => {
      if (frame.id === frameId) {
        return {
          ...frame,
          [field]: value,
        };
      }
      return frame;
    });

    setStories(newStories);
  };

  return (
    <div>
      <ul className={styles.stories}> {/* Использование CSS-модуля */}
        <h2>{platform}</h2>
        {stories.map((story, index) => (
          <li className={styles.listFrame} key={index}> {/* Использование стиля через styles */}
            <details>
              <summary draggable="true">
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
              <div style={{ display: "flex" }}>
                {showStory ? (
                  // История с показом её превью
                  <>
                    <div className={styles["story-with-preview"]}> {/* Применение стиля через объект styles */}
                      <StoryCard
                        key={index}
                        story={story}
                        storyIndex={index}
                        platform={platform}
                        changeStory={handleUpdateStory}
                      />
                    </div>
                    <div className={styles.preview}> {/* Использование стиля preview через объект styles */}
                      <ShowStory stories={storyArray[index]} />
                    </div>
                  </>
                ) : (
                  // История без показа превью
                  <div className={styles["story-without-preview"]}>
                    <StoryCard
                      key={index}
                      story={story}
                      storyIndex={index}
                      platform={platform}
                    />
                  </div>
                )}
              </div>
            </details>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default StoryPanel;
