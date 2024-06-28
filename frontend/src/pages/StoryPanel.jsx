import React from 'react';
import StoryCard from './StoryCard';
import  './StoryPanelStyle.css';

const StoryPanel = ({ storyArray }) => {
    return(
        <div className='tag-list'>
            <ul>
            {storyArray.map((story, index) => (
                <li key={index}>
                <details>
                <summary>{story.previewTitle+" " +index}</summary>
                <StoryCard key={index} story={story} />
                </details>
                </li>
            ))}
            </ul>
        </div>
    );
  }


export default StoryPanel;
