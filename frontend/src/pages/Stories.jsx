import { FieldArray, Form, Formik } from "formik";
import React from "react";

import { uploadStories } from "../api/stories";
import { initialStoryValues } from "../utils/constants/initialValues";
import { convertToPayload } from "../utils/helpers/byteArrayFunctions";
import { storyValidationSchema } from "../utils/helpers/validation";

import CommonForm from "../components/Stories/CommonForm";
import StoryForm from "../components/Stories/StoryForm";
import AlertMessage from "../components/ui/AlertMessage";
import Button from "../components/ui/Button";
import Loader from "../components/ui/Loader/index";

const Stories = () => {
  const [send, setSend] = React.useState(false);
  const [success, setSuccess] = React.useState(true);
  const [loading, setLoading] = React.useState(false);

  const handleOnSubmit = async (values, { resetForm }) => {
    setSend(false);
    setLoading(true);
    const payload = await convertToPayload(values);
    let images = [];
    images[0] = values.stories[0].previewUrl === null ? null : values.stories[0].previewUrl;
    images[1] = values.stories[0].storyFrames[0].pictureUrl;
    const jsonPayload = JSON.stringify(payload, null, 2);
    const uploadResult = await uploadStories(jsonPayload, images).finally(() => {
      setSend(true);
      setLoading(false);
    });
    setSuccess(uploadResult);
    if (uploadResult) {
      resetForm(initialStoryValues);
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
              <CommonForm {...props} />
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
