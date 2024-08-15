import "../ExistStory/StoryPanelStyle.css";
import Button from "../../ui/Button/index";
import { deleteStoryFromDb } from "../../../api/stories";
import { useState, useEffect, React } from "react";
import api from "../../../api/api";
import StoryCard from "../ExistStory/StoryCard";

const StoryUnApprovedCard = ({ storyArray, platform }) => {
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
      const success = await deleteStoryFromDb(story, platform);
      if (success) {
        setStories((prevStories) =>
          prevStories.filter((item) => item.id !== story.id),
        );
      }
    } catch (error) {
      console.error("Ошибка при удалении истории", error);
    }
  };
  const approve = async (story, platform) => {
    try {
      const success = await api.post(`/stories/admin/approveStory?bankId=${story.bankId}&platform=${platform}&id=${story.id}`);
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
                  <div className="row">
                    <Button
                      text="Удалить из бд"
                      type="button"
                      color="red"
                      handleOnClick={() => handleOnSubmit(story, platform)}
                    />
                    <Button
                      text="Принять"
                      type="button"
                      color="green"
                      handleOnClick={() => approve(story, platform)}
                    />
                  </div>
                </div>
              </summary>
              <div>
                <StoryCard
                  key={index}
                  story={story}
                  storyIndex={index}
                  platform={platform}
                />
              </div>
            </details>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default StoryUnApprovedCard;
