import React from 'react';
import { Formik, Form, FieldArray } from 'formik';

import api from '../api/stories';
import { storyValidationSchema } from '../utils/helpers/validation';
import { initialStoryValues, initialStoryFrame } from '../utils/constants/initialValues';
import { banks } from '../utils/constants/banks';

import Button from './Button';
import PreviewFields from './StoryFormParts/PreviewFields';
import FrameFields from './StoryFormParts/FrameFields';
import FormField from './FormField';
import AlertMessage from './AlertMessage';
import Loader from './Loader';

import { ReactComponent as ArrowIcon } from '../assets/icons/arrow-up.svg';

const maxFrames = 6;

const StoryForm = () => {
  const [send, setSend] = React.useState(false);
  const [success, setSuccess] = React.useState(true);
  const [loading, setLoading] = React.useState(false);

  const handleOnSubmit = async (values, { resetForm }) => {
    setSend(false);
    setLoading(true);
    const jsonValues = JSON.stringify(values, null, 2);
    try {
      await api.post('/add', jsonValues);
      resetForm(initialStoryValues);
      setSuccess(true);
      setSend(true);
      setTimeout(() => {
        setSend(false);
      }, 2000);
    } catch (error) {
      setSuccess(false);
      setSend(true);
      if (error.response) {
        console.log(error.response.data);
      } else {
        console.log(`Error ${error.message}`);
      }
    }
    setLoading(false);
    console.log(JSON.stringify(values, null, 2));
  };

  return (
    <div>
      <Formik
        enableReinitialize
        initialValues={initialStoryValues}
        validationSchema={storyValidationSchema}
        onSubmit={handleOnSubmit}>
        {(props) => (
          <Form>
            {loading && <Loader />}
            <div className="input_field">
              <FormField
                labelTitle={'Банк'}
                name={`bankId`}
                as="select"
                options={banks.map((bank) => {
                  return { value: bank.id, name: bank.name };
                })}
                errors={props.errors}
                touched={props.touched}
              />
            </div>
            <h2>Превью</h2>
            <FieldArray name="stories">
              {() => (
                <>
                  {props.values.stories.map((story, indexS) => (
                    <div key={indexS}>
                      <PreviewFields
                        storyIndex={indexS}
                        setFieldValue={props.setFieldValue}
                        errors={props.errors}
                        touched={props.touched}
                      />
                      <br />
                      <h2>Карточки</h2>
                      <FieldArray name={`stories.${indexS}.storyFrames`}>
                        {({ remove, push }) => (
                          <>
                            {story.storyFrames.map((storyFrame, index) => (
                              <FrameFields
                                key={index}
                                storyIndex={indexS}
                                frameIndex={index}
                                framesCount={story.storyFrames.length}
                                setFieldValue={props.setFieldValue}
                                frameJson={storyFrame}
                                remove={remove}
                                errors={props.errors}
                                touched={props.touched}
                              />
                            ))}
                            {story.storyFrames.length < maxFrames && (
                              <Button
                                text="Добавить"
                                type="button"
                                color="green"
                                icon={<ArrowIcon width="12px" height="12px" />}
                                handleOnClick={() => push(initialStoryFrame)}
                              />
                            )}
                          </>
                        )}
                      </FieldArray>
                    </div>
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
  );
};

export default StoryForm;
