import React from 'react';

const StoryFrame = ({ frame }) => {
  return (
    <li className="frames">
      <p>Title: {frame.title}</p>
      <p>Text: {frame.text}</p>
      <p>Text Color: {frame.textColor}</p>
      <p>Visible Link or Button or None: {frame.visibleLinkOrButtonOrNone}</p>
      <p>Button Text: {frame.buttonText}</p>
      <p>Button Text Color: {frame.buttonTextColor}</p>
      <p>Button Background Color: {frame.buttonBackgroundColor}</p>
      <img className='pict' src={"http://localhost:8080"+frame.pictureUrl} alt="Story Frame" />
      {console.log("http://localhost:8080"+frame.pictureUrl)}
    </li>
  );
}

export default StoryFrame;
