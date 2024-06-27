import { FieldArray, Form, Formik } from "formik";
import React from "react";

import { uploadStories } from "../api/stories";
import { initialBannerFrame } from "../utils/constants/initialValues";
import { convertToPayload } from "../utils/helpers/byteArrayFunctions";
import { storyValidationSchema } from "../utils/helpers/validation";

import CommonForm from "../components/Stories/CommonForm";
import BannerForm from "../components/Banners/BannerForm";
import AlertMessage from "../components/ui/AlertMessage";
import Button from "../components/ui/Button";
import Loader from "../components/ui/Loader/index";

const Banners = () => {
  const [send, setSend] = React.useState(false);
  const [success, setSuccess] = React.useState(true);
  const [loading, setLoading] = React.useState(false);

  const handleOnSubmit = async (values, { resetForm }) => {
    setSend(false);
    setLoading(true);
    const payload = await convertToPayload(values);
    const previewImage = values.stories[0].previewUrl;
    const cardImages = values.stories[0].storyFrames.map((storyFrame) => {
      return storyFrame.pictureUrl;
    });
    const jsonPayload = JSON.stringify(payload, null, 2);
    const uploadResult = await uploadStories(jsonPayload, previewImage, cardImages).finally(() => {
      setSend(true);
      setLoading(false);
    });
    setSuccess(uploadResult);
    if (uploadResult) {
      resetForm(initialBannerFrame);
    }
  };

  return (
    <>
      {loading && <Loader />}
      <h1>Добавить Story</h1>
      <div className="stories">
        <Formik
          enableReinitialize
          initialValues={initialBannerFrame}
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
                      <BannerForm
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

export default Banners;
