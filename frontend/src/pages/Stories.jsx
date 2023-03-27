import React from 'react';
import { Formik, Form, FieldArray } from 'formik';

import { convertToPayload } from '../utils/helpers/byteArrayFunctions';
import { initialStoryValues } from '../utils/constants/initialValues';
import { storyValidationSchema } from '../utils/helpers/validation';
import api from '../api/stories';

import Button from '../components/ui/Button';
import Loader from '../components/ui/Loader';
import AlertMessage from '../components/ui/AlertMessage';
import StoryForm from '../components/StoryForm';

const Stories = () => {
  const [send, setSend] = React.useState(false);
  const [success, setSuccess] = React.useState(true);
  const [loading, setLoading] = React.useState(false);

  const handleOnSubmit = async (values, { resetForm }) => {
    setSend(false);
    setLoading(true);
    const payload = await convertToPayload(values);
    const jsonPayload = JSON.stringify(payload, null, 2);
    try {
      await api.post('/add', jsonPayload);
      resetForm(initialStoryValues);
      setSuccess(true);
      setSend(true);
      setTimeout(() => {
        setSend(false);
      }, 2000);
    } catch (error) {
      setSuccess(false);
      setSend(true);
    } finally {
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
          onSubmit={handleOnSubmit}>
          {(props) => (
            <Form>
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
