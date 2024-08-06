import  '../ExistStory/StoryPanelStyle.css';
import Button from './../../ui/Button/index';
import { ReactComponent as ArrowIcon } from '../../../assets/icons/arrow-up.svg';
import { useState, useEffect, React } from 'react';
import StoryCard from '../ExistStory/StoryCard';
import api from '../../../api/api';
import { deleteStoryFromDb } from '../../../api/stories';

const StoryAdminPanel = ({ storyArray, storyDeleted}) => {
  const [stories, setStories] = useState(storyArray);
  const [storiesDeleted, setStoryDeleted] = useState(storyDeleted);
  //Для обновления после удаления
  useEffect(() => {
    if (storyArray) {
      setStories(storyArray);
    }
    setStoryDeleted(storyDeleted);
  }, [storyArray, storyDeleted]);
  //Для удаления истории
  const deleteFromDb = async (story) => {
    try {
      const success = await deleteStoryFromDb(story, story.platform);
      if (success) {
        setStoryDeleted(prevStories => prevStories.filter(item => item.id !== story.id));
      }
    }
    catch (error) {
      console.error('Ошибка при удалении истории', error);
    }
  };
  const deleteFromUnApproved = async (story) => {
    try {
      const success = await deleteStoryFromDb(story, story.platform);
      if (success) {
        setStories(prevStories => prevStories.filter(item => item.id !== story.id));
      }
    }
    catch (error) {
      console.error('Ошибка при удалении истории', error);
    }
  };
  const approved = async (bankId, platform, storyId) => {
    try {
      const success = api.post(`/stories/admin/approveStory?bank=${bankId}&platform=${platform}&id=${storyId.id}`);
      if (success) {
        setStories(prevStories => prevStories.filter(item => item.id !== storyId.id));
      }
    }
    catch (error) {
      console.error('Ошибка при удалении истории', error);
    }
  }
  const restoreStory = async (storyId) => {
    api.patch(`/stories/admin/bank/info/restore/story?id=${storyId.id}`);
  }

    return(
        <div>
            <h1>Непринятые истории</h1>
            <ul className="stories">
            {stories.map((story, index) => (
                <li className='listFrame' key={index}>
                <details>
                  <summary>
                    <p>{story.previewTitle}</p>
                    <p>{story.bankId}</p>
                    <p>{`${story.platform}`}</p>
                    <div>
                      <div className='row'>
                        <Button
                            handleOnClick={() => approved(story.bankId, story.platform, story)}
                            text="Сохранить"
                            type="button"
                            color="green"
                        />
                        <Button
                              text="Удалить"
                              type="button"
                              color="red"
                              handleOnClick={() => deleteFromUnApproved(story, story.platform)}
                              icon={<ArrowIcon width="12px" height="12px" />}
                            />
                      </div>
                    </div>
                  </summary>
                  <div>
                     <StoryCard key={index} story={story} storyIndex={index} platform={story.platform}/>
                  </div>
                </details>
                </li>
            ))}
            </ul>
            <h1>Удаленные истории</h1>
            <ul className="stories">
            {storiesDeleted.map((story, index) => (
                <li className='listFrame' key={index}>
                <details>
                  <summary>
                    <p>{story.previewTitle}</p>
                    <p>{story.bankId}</p>
                    <p>{`${story.platform}`}</p>
                    <div>
                      <div className='row'>
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
                              icon={<ArrowIcon width="12px" height="12px" />}
                            />
                      </div>
                    </div>
                  </summary>
                  <div>
                     <StoryCard key={index} story={story} storyIndex={index} platform={story.platform}/>
                  </div>
                </details>
                </li>
               ))}
              </ul>
        </div>
    );
  }

export default StoryAdminPanel;
