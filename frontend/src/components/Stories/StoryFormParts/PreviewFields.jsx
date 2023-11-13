import React from 'react';

import { gradientOptions } from '../../../utils/constants/gradient';
import ColorPicker from '../../ColorPicker';
import FormField from '../../FormField';
import UploadImage from '../../UploadImage';

const PreviewFields = ({ storyIndex, ...props }) => {
  return (
    <>
      <div className="row">
        <div className="input_field">
          <FormField
            className="title"
            labelTitle={'Заголовок'}
            name={`stories.${storyIndex}.previewTitle`}
            as={'textarea'}
            {...props}
          />
        </div>
        <FormField
          labelTitle={'Цвет текста'}
          name={`stories.${storyIndex}.previewTitleColor`}
          component={ColorPicker}
          {...props}
        />
      </div>
      <div className="input_field">
        <FormField
          labelTitle={'Картинка'}
          name={`stories.${storyIndex}.previewUrl`}
          component={UploadImage}
          {...props}
        />
      </div>
      <div className="input_field">
        <FormField
          labelTitle={'Градиент'}
          name={`stories.${storyIndex}.previewGradient`}
          as="select"
          options={gradientOptions}
          {...props}
        />
      </div>
    </>
  );
};

export default PreviewFields;
