import "../ExistStory/StoryPanelStyle.css";
import Button from "./../../ui/Button/index";
import { useState, useEffect, React } from "react";
import api from "../../../api/api";
import StoryCard from "../ExistStory/StoryCard";
import { deleteStoryFromDb } from "../../../api/stories";

const StoryDeletedPanel = ({ storyArray, platform }) => {
  const [stories, setStories] = useState(storyArray);
  //Для обновления после удаления
  useEffect(() => {
    if (storyArray) {
      setStories(storyArray);
    }
  }, [storyArray]);

    //Для удаления истории
    const deleteFromDb = async (story) => {
        try {
          const resp = await deleteStoryFromDb(story, story.platform);
          console.log(story);
          if (resp){
            setStories((prevStories) =>
                prevStories.filter((item) => item.id !== story.id),
              )
          }
        }
        catch (error) {
          console.error('Ошибка при удалении истории из базы данных', error);
        }
      };
      const restoreStory = async (story) => {
        try {
          const resp = api.patch(`/stories/admin/bank/info/restore/story?id=${story.id}`);
          if (resp){
            setStories((prevStories) =>
                prevStories.filter((item) => item.id !== story.id),
              )
          }
        }
        catch (error) {
          console.error('Ошибка при восстановлении истории', error);
        }
      }


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
                            handleOnClick={() => restoreStory(story)}
                            text="Восстановить"
                            type="button"
                            color="green"
                        />
                        <Button
                              text="Удалить из БД"
                              type="button"
                              color="red"
                              handleOnClick={() => deleteFromDb(story, story.platform)}
                            />
                  </div>
                </div>
              </summary>
              <div>
                <StoryCard
                  key={index}
                  story={story}
                  storyIndex={index}
                  changeable={true}
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

export default StoryDeletedPanel;
