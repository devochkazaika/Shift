import React, { useState, useEffect } from "react";
import {
  fetchImage,
} from "../../../api/stories";
import styles from "./StoryCard.module.scss";
import PreviewStory from "./PreviewStory";
import StoryFrames from "./StoryFrames";

/**
 * @param {number} storyIndex - Порядковый номер истории внутри story.
 * @param {Array} story - Список историй.
 * @param {('IOS' | 'WEB' | 'ANDROID' | 'ALL_PLATFORMS')} platform - Платформа (IOS | WEB | ANDROID | ALL_PLATFORMS).
 * @param {boolean} changeStory - Флаг, отвечающий за показ компонентов для изменения.
 * @param {boolean} changeable - Можно ли изменять историю или нет.
 * @param {boolean} showStory - Показывать ли превью истории или нет.
 */
const StoryCard = ({ storyIndex, story, platform, changeable, ...props }) => {
  const [frames, setFrames] = useState(story.storyFrames || []);
  const [initialImage, setInitialImage] = useState(null);

  useEffect(() => {
    if (!initialImage && story.previewUrl) {
      fetchImage(story.previewUrl, setInitialImage);
    }
    if (story && story.storyFrames) {
      setFrames(story.storyFrames);
    }
  }, [story, initialImage]);

  return (
    <div className={styles.storyCard}>
      {/* Превью истории */}
      <PreviewStory
        story={story}
        storyIndex={storyIndex}
        initialImage={initialImage}
        changeable={changeable}
        platform={platform}
        {...props}
      />
      {/* Карточки истории */}
      <StoryFrames
        frames={frames}
        setFrames={setFrames}
        storyIndex={storyIndex}
        story={story}
        platform={platform}
        changeable={changeable}
      />
    </div>
  );
};

export default StoryCard;