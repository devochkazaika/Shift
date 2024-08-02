import React, { useEffect, useState } from 'react';
import CommonForm from '../components/Stories/CommonForm';
import { getUnApprovedStories } from '../api/stories';
import { Formik } from 'formik';
import StoryAdminPanel from '../components/Stories/UnApprovedStory/StoryAdminPanel';

const StoryAdmin = () => {
  const [bankId, setBankId] = useState("absolutbank");
  const [platform, setPlatform] = useState("ALL PLATFORMS");
  const [storyArray, setStoryArray] = useState([]);

  useEffect(() => {
    const fetchDataAsync = async () => {
      try {
        const data = await getUnApprovedStories(bankId, platform);
        setStoryArray(data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchDataAsync();
  }, [bankId, platform]);
  return <div>
    {storyArray.length > 0 ? 
      <StoryAdminPanel storyArray={storyArray} platform={platform}/> : <></>}
    <Formik
      enableReinitialize
      initialValues={{
        bankId: "absolutbank",
        platform: "ALL PLATFORMS"
      }}
    >
      {(props) => {
        <CommonForm setBankId={setBankId} setPlatform={setPlatform} {...props}/>
      }}
    </Formik>
  </div>;
};

export default StoryAdmin;