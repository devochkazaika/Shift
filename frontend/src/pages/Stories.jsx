import React from 'react';

import StoryForm from '../components/StoryForm';

const Stories = () => {
  return (
    <>
      <h1>Добавить Story</h1>
      <div className="stories">
        <StoryForm />
      </div>
    </>
  );
};

export default Stories;
