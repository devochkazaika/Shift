import styles from "../../../styles/StoryPanelStyle.module.scss";
import Button from "./../../ui/Button/index";
import { useState, useEffect, React } from "react";
import api from "../../../api/api";
import StoryCard from "../ExistStory/StoryCard";
import { deleteStoryFromDb } from "../../../api/stories";

/**
 * Панель для работы с удаленными историями.
 * Позволяет восстановить или окончательно удалить историю.
 *
 * @param {Object} props - Свойства компонента.
 * @param {Array} props.storyArray - Массив историй, отображаемых в панели.
 * @param {string} props.platform - Платформа, к которой относится история (например, IOS, WEB, ANDROID, ALL PLATFORMS).
 */
const StoryDeletedPanel = ({ storyArray, platform }) => {
  const [stories, setStories] = useState(storyArray);

  /**
   * Обновляет массив историй при изменении storyArray.
   */
  useEffect(() => {
    if (storyArray) {
      setStories(storyArray);
    }
  }, [storyArray]);

  /**
   * Удаляет историю из базы данных.
   *
   * @param {Object} story - История, которую нужно удалить.
   */
  const deleteFromDb = async (story) => {
    try {
      const resp = await deleteStoryFromDb(story, story.platform);
      if (resp) {
        setStories((prevStories) =>
          prevStories.filter((item) => item.id !== story.id)
        );
      }
    } catch (error) {
      console.error('Ошибка при удалении истории из базы данных', error);
    }
  };

  /**
   * Восстанавливает удаленную историю.
   *
   * @param {Object} story - История, которую нужно восстановить.
   */
  const restoreStory = async (story) => {
    try {
      const resp = await api.patch(`/stories/admin/bank/info/restore/story?id=${story.id}`);
      if (resp) {
        setStories((prevStories) =>
          prevStories.filter((item) => item.id !== story.id)
        );
      }
    } catch (error) {
      console.error('Ошибка при восстановлении истории', error);
    }
  };

  return (
    <div>
      <ul className="stories">
        <h2>{platform}</h2>
        {stories.map((story, index) => (
          <li className={styles["listFrame"]} key={index}>
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
