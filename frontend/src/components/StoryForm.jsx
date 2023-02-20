import React from 'react';
import { FieldArray } from 'formik';

import { banks } from '../utils/constants/banks';
import { initialStoryFrame, maxFrames } from '../utils/constants/initialValues';

import Button from './ui/Button';
import PreviewFields from './StoryFormParts/PreviewFields';
import FrameFields from './StoryFormParts/FrameFields';
import FormField from './FormField';

import { ReactComponent as ArrowIcon } from '../assets/icons/arrow-up.svg';

const StoryForm = ({ storyIndex, storyJson, ...props }) => {
  return (
    <div>
      <h2>Банк</h2>
      <div className="input_field">
        <FormField
          name={`bankId`}
          as="select"
          options={banks.map((bank) => {
            return { value: bank.id, name: bank.name };
          })}
          {...props}
        />
      </div>

      <h2>Превью</h2>
      <PreviewFields storyIndex={storyIndex} {...props} />
      <br />

      <h2>Карточки</h2>
      <FieldArray name={`stories.${storyIndex}.storyFrames`}>
        {({ remove, push }) => (
          <>
            {storyJson.storyFrames.map((storyFrame, frameIndex) => (
              <FrameFields
                key={frameIndex}
                storyIndex={storyIndex}
                frameIndex={frameIndex}
                frameJson={storyFrame}
                framesCount={storyJson.storyFrames.length}
                remove={remove}
                {...props}
              />
            ))}
            {storyJson.storyFrames.length < maxFrames && (
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
  );
};

export default StoryForm;
