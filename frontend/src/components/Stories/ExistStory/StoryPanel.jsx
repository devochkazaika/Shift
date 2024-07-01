import React from 'react';
import StoryCard from './StoryCard';
import  './StoryPanelStyle.css';
// import styles from "../StoryFormParts/StoryFormParts.module.scss";
import Button from './../../ui/Button/index';
import { ReactComponent as ArrowIcon } from '../../../assets/icons/arrow-up.svg';
import { deleteStory } from './../../../api/stories';


const StoryPanel = ({ storyArray, platform }) => {
    const handleOnSubmit = async (values, platform) => {
        deleteStory(values, platform)
      };

    return(
        <div>
            <ul className="stories">
            {storyArray.map((story, index) => (
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
                     <StoryCard key={index} story={story} storyIndex={index} />
                  </div>
                </details>
                </li>
            ))}
            </ul>
        </div>
    );
  }


export default StoryPanel;
