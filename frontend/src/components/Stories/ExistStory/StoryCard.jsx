import React from 'react';
import StoryFrame from './StoryFrame';
// import styles from "./StoryFormParts/StoryFormParts.module.scss";

const StoryCard = ({ story }) => {
  return (
    <div>
      <p>Bank ID: {story.bankId}</p>
      <p>Preview Title: {story.previewTitle}</p>
      
      <img className='preview' src={"http://localhost:8080"+story.previewUrl} alt="Story Frame" />
      <p>previewGradient: {"asdasd"}</p>
      {/* Другие поля для отображения */}
      <div>
        <h3>Story Frames:</h3>
        <ul className='card'>
            {story.storyFrames.map((value, index) =>(
                <StoryFrame key={index} frame={value}/>
            ))}
        </ul>
      </div>
    </div>
  );
}

export default StoryCard;
