import React from 'react';

import FormField from '../../FormField';
import ColorPicker from '../../ColorPicker';

import previewFieldsStyles from './PreviewFields.module.scss';
import storyFormPartsStyles from '../StoryFormParts.module.scss';

const PreviewFields = ({ storyIndex }) => {
  return (
    <>
      <div className={storyFormPartsStyles.row}>
        <div className={storyFormPartsStyles.input_field}>
          <FormField
            labelTitle={'Заголовок истории'}
            name={`stories.${storyIndex}.previewTitle`}
            type={'text'}
          />
        </div>
        <FormField
          labelTitle={'Цвет текста'}
          name={`stories.${storyIndex}.previewTitleColor`}
          component={ColorPicker}
        />
      </div>
      <div className={storyFormPartsStyles.input_field}>
        <FormField
          labelTitle={'Путь до картинки'}
          name={`stories.${storyIndex}.previewUrl`}
          type="text"
        />
      </div>
      <div className={storyFormPartsStyles.input_field}>
        <FormField
          labelTitle={'Градиент истории'}
          name={`stories.${storyIndex}.previewGradient`}
          as="select"
          options={[
            { value: 'EMPTY', name: 'Нет' },
            { value: 'FULL', name: 'Поверх всего изображения' },
          ]}
        />
      </div>
    </>
  );
};

export default PreviewFields;
