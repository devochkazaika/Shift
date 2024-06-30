import React from 'react';
import StoryCard from './StoryCard';
import  './StoryPanelStyle.css';
// import styles from "../StoryFormParts/StoryFormParts.module.scss";

const StoryPanel = ({ storyArray }) => {
    return(
        <div>
            <ul className="stories">
            {storyArray.map((story, index) => (
                <li className='stories' key={index}>
                <details>
                <summary>{story.previewTitle+" " +story.id}</summary>
                <StoryCard key={index} story={story} />
                </details>
                </li>
            ))}
            </ul>
        </div>
    );
  }


export default StoryPanel;
