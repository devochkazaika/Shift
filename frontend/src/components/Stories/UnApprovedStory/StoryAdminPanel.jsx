import  '../ExistStory/StoryPanelStyle.css';

import { useState, useEffect, React } from 'react';
import StoryCard from '../ExistStory/StoryCard';


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
