import React from 'react';
import StoryFrame from './StoryFrame';

const StoryCard = ({ story }) => {
  return (
    <div>
      <p>Bank ID: {story.bankId}</p>
      <p>Preview Title: {story.previewTitle}</p>
      <p>previewTitleColor: {story.previewTitleColor}</p>
      {/* Другие поля для отображения */}
      <div>
        <h3>Story Frames:</h3>
        {story.storyFrames.map((value, index) =>(
            <StoryFrame key={index} frame={value}/>
        ))}
      </div>
    </div>
  );
}

export default StoryCard;
