import React from 'react';

const StoryFrame = ({ frame }) => {
  return (
    <div className="story-frame">
      <p>Title: {frame.title}</p>
      <p>Text: {frame.text}</p>
      <p>Text Color: {frame.textColor}</p>
      <p>Visible Link or Button or None: {frame.visibleLinkOrButtonOrNone}</p>
      <p>Button Text: {frame.buttonText}</p>
      <p>Button Text Color: {frame.buttonTextColor}</p>
      <p>Button Background Color: {frame.buttonBackgroundColor}</p>
      <img src="F:\content-maker\backend\src\main\resources\site\share\htdoc\_files\skins\mobws_story\tkbbank\WEB\0_0.jpg" alt="Story Frame" />
    </div>
  );
}

export default StoryFrame;
