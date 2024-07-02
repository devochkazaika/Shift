import StoryCard from './StoryCard';
import  './StoryPanelStyle.css';
import Button from './../../ui/Button/index';
import { ReactComponent as ArrowIcon } from '../../../assets/icons/arrow-up.svg';
import { deleteStory } from './../../../api/stories';
import { useState, useEffect, React } from 'react';



const StoryPanel = ({ storyArray, platform }) => {
  const [stories, setStories] = useState(storyArray);
  //Для обновления после удаления
  useEffect(() => {
    if (storyArray && storyArray.length) {
      setStories(storyArray);
    }
  }, [storyArray]);
  //Для удаления истории
  const handleOnSubmit = async (story, platform) => {
    const success = await deleteStory(story, platform);
    if (success) {
      setStories(prevStories => prevStories.filter(item => item.id !== story.id));
    }
  };

    return(
        <div>
            <ul className="stories">
            {stories.map((story, index) => (
                <li className='listFrame' key={index}>
                <details className="item-card">
                  <summary className="item-card__summary">
                    <p className="item-card__title">{story.previewTitle}</p>
                    <div className="item-card__buttons">
                      <div className="item-card__button--delete">
                        <Button
                              text="Удалить"
                              type="button"
                              color="red"
                              handleOnClick={() => handleOnSubmit(story, platform)}
                              icon={<ArrowIcon width="12px" height="12px" />}
                            />
                      </div>
                    </div>
                  </summary>
                  <div className="item-card__content">
                     <StoryCard key={index} story={story} storyIndex={index} platform={platform}/>
                  </div>
                </details>
                </li>
            ))}
            </ul>
        </div>
    );
  }


export default StoryPanel;
