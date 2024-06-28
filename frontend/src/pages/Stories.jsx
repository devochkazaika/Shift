import { FieldArray, Form, Formik } from "formik";
import React, { useEffect, useState } from "react";

import { uploadStories } from "../api/stories";
import { initialStoryValues } from "../utils/constants/initialValues";
import { convertToPayload } from "../utils/helpers/byteArrayFunctions";
import { storyValidationSchema } from "../utils/helpers/validation";

import CommonForm from "../components/Stories/CommonForm";
import StoryForm from "../components/Stories/StoryForm";
import AlertMessage from "../components/ui/AlertMessage";
import Button from "../components/ui/Button";
import Loader from "../components/ui/Loader/index";
import fetchData from './StoryArray'; // Предполагая, что это функция для загрузки данных
// import StoryCard from "./StoryCard";
import StoryPanel from "./StoryPanel";

const Stories = () => {
  const [send, setSend] = useState(false);
  const [success, setSuccess] = useState(true);
  const [loading, setLoading] = useState(false);
  const [storyArray, setStoryArray] = useState([]);
  const [bankId, setBankId] = useState('tkbbank');
  const [platform, setPlatform] = useState('WEB');

  useEffect(() => {
    const fetchDataAsync = async () => {
      try {
        const data = await fetchData(bankId, platform);
        setStoryArray(data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchDataAsync();
  }, [bankId, platform]);

  const handleOnSubmit = async (values, { resetForm }) => {
    setSend(false);
    setLoading(true);
    try {
      const payload = await convertToPayload(values);
      const previewImage = values.stories[0].previewUrl;
      const cardImages = values.stories[0].storyFrames.map((storyFrame) => {
        return storyFrame.pictureUrl;
      });
      const jsonPayload = JSON.stringify(payload, null, 2);
      const uploadResult = await uploadStories(jsonPayload, previewImage, cardImages);
      setSuccess(uploadResult);
      if (uploadResult) {
        resetForm(initialStoryValues);
      }
    } catch (error) {
      console.error('Error uploading stories:', error);
    } finally {
      setSend(true);
      setLoading(false);
    }
  };

  return (
    <>
      {console.log(storyArray)}
      <StoryPanel storyArray={storyArray} />
      {loading && <Loader />}
      <h1>Добавить Story</h1>
      <div className="stories">
        <Formik
          enableReinitialize
          initialValues={initialStoryValues}
          validationSchema={storyValidationSchema}
          onSubmit={handleOnSubmit}
        >
          {(props) => (
            <Form>
              <CommonForm setBankId = {setBankId} setPlatform = {setPlatform}
                {...props}
              />
              <FieldArray name="stories">
                {() => (
                  <>
                    {props.values.stories.map((story, storyIndex) => (
                      <StoryForm
                        key={storyIndex}
                        storyIndex={storyIndex}
                        storyJson={story}
                        {...props}
                      />
                    ))}
                  </>
                )}
              </FieldArray>

              <Button text="Отправить" type="submit" color="red" />
              {send && <AlertMessage success={success} />}
            </Form>
          )}
        </Formik>
      </div>
    </>
  );
};

export default Stories;
