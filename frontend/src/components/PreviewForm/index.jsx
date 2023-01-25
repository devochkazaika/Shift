import React from 'react';

import FormField from '../FormField';
import ColorPicker from '../ColorPicker';

const PreviewForm = ({ storyIndex }) => {
  return (
    <>
      <FormField
        labelTitle={'Заголовок истории'}
        name={`stories.${storyIndex}.previewTitle`}
        type={'text'}
      />
      <FormField
        labelTitle={'Цвет текста'}
        name={`stories.${storyIndex}.previewTitleColor`}
        component={ColorPicker}
      />
      <FormField
        labelTitle={'Путь до картинки'}
        name={`stories.${storyIndex}.previewUrl`}
        type="text"
      />
      <FormField
        labelTitle={'Градиент истории'}
        name={`stories.${storyIndex}.previewGradient`}
        as="select"
        options={[
          { value: 'EMPTY', name: 'Нет' },
          { value: 'FULL', name: 'Поверх всего изображения' },
        ]}
      />
    </>
  );
};

export default PreviewForm;
