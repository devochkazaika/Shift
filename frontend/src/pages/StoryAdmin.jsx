import React, { useEffect, useState } from 'react';
import { getUnApprovedStories } from '../api/stories';
import StoryAdminPanel from '../components/Stories/UnApprovedStory/StoryAdminPanel';
import { getDeletedStories } from './../api/stories';

const StoryAdmin = () => {
  const [storyArray, setStoryArray] = useState([]);
  const [storyDeleted, setStoryDeleted] = useState([]);

  useEffect(() => {
    const fetchDataAsync = async () => {
      try {
        const data = await getUnApprovedStories();
        setStoryArray(data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchDataAsync();

    const getDelStories = async () => {
      try {
        const data = await getDeletedStories();
        setStoryDeleted(data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    getDelStories();
  }, []); 

  
  return <div>
    { <StoryAdminPanel storyArray={storyArray} storyDeleted={storyDeleted}/>}
  </div>;
};

export default StoryAdmin;