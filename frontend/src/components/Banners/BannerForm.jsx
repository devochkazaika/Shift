import { FieldArray } from 'formik';
import React from 'react';

import { initialStoryFrame, maxFrames } from '../../utils/constants/initialValues';

import Button from '.././ui/Button';
import FrameFields from './BannerFormParts/FrameFields';
import PreviewFields from './BannerFormParts/PreviewFields';

import { ReactComponent as ArrowIcon } from '../../assets/icons/arrow-up.svg';

const BannerForm = ({ storyIndex, storyJson, ...props }) => {
  return (
    <div>
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

export default BannerForm;
