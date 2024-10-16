import { FieldArray, Form, Formik } from "formik";
import React, { useState } from "react";

import {uploadStories } from "../api/stories";
import { initialStoryValues } from "../utils/constants/initialValues";
import { convertToPayload } from "../utils/helpers/byteArrayFunctions";
import { storyValidationSchema } from "../utils/helpers/validation";

import CommonForm from "../components/Stories/CommonForm";
import StoryForm from "../components/Stories/StoryForm";
import AlertMessage from "../components/ui/AlertMessage";
import Button from "../components/ui/Button";
import Loader from "../components/ui/Loader/index";


const Stories = () => {
  const [send, setSend] = useState(false);
  const [success, setSuccess] = useState(true);
  const [loading, setLoading] = useState(false);
  const [bankId, setBankId] = useState("absolutbank");
  const [platform, setPlatform] = useState("ALL PLATFORMS");


  const handleOnSubmit = async (values, { resetForm }) => {
    setSend(false);
    setLoading(true);
    try {
      const previewImage = values.stories[0].previewUrl;
      values.stories[0].previewUrl = "";
      const cardImages = values.stories[0].storyFrames.map((storyFrame) => {
        let k = storyFrame.pictureUrl; 
        storyFrame.pictureUrl = "";
        return k;
      });
      
      const payload = await convertToPayload(values);
      const jsonPayload = JSON.stringify(payload, null, 2);
      const uploadResult = await uploadStories(
        jsonPayload,
        previewImage,
        cardImages,
      );
      setSuccess(uploadResult);
      if (uploadResult) {
        resetForm(initialStoryValues);
      }
    } catch (error) {
      console.error("Error uploading stories:", error);
    } finally {
      setSend(true);
      setLoading(false);
    }
  };

  return (
    <>
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
              <CommonForm
                setBankId={setBankId}
                setPlatform={setPlatform}
                bankId={bankId}
                platform={platform}
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
