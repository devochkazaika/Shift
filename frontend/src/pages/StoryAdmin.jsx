import React, { useEffect, useState } from 'react';
import CommonForm from '../components/Stories/CommonForm';
import { getUnApprovedStories } from '../api/stories';
import { Formik } from 'formik';
import StoryPanel from '../components/Stories/ExistStory/StoryPanel';
import Button from '../components/ui/Button';

const StoryAdmin = () => {
  const [bankId, setBankId] = useState("absolutbank");
  const [platform, setPlatform] = useState("ALL PLATFORMS");
  const [storyArray, setStoryArray] = useState([]);

  const approved = async (bankId, platform, storyId) => {

  }

  const button = {
    {
      <Button handleOnClick={}
    }
  }
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
    {storyArray.length > 0 ? <StoryPanel storyArray={storyArray} platform={platform} /> : <></>}
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