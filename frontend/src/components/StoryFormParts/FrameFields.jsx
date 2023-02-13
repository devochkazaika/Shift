import React from 'react';

import FormField from '../FormField';
import ColorPicker from '../ColorPicker';
import UploadImage from '../UploadImage';

import { ReactComponent as MinusIcon } from '../../assets/icons/minus.svg';
import { ReactComponent as PlusIcon } from '../../assets/icons/plus.svg';

import styles from './StoryFormParts.module.scss';

const FrameFields = ({
  frameJson,
  storyIndex,
  frameIndex,
  framesCount,
  remove,
  errors,
  touched,
}) => {
  const [open, setOpen] = React.useState(true);
  const gradientOptions = [
    { value: 'EMPTY', name: 'Нет' },
    { value: 'FULL', name: 'Поверх всего изображения' },
    { value: 'HALF', name: 'Поверх нижней половины изображения' },
  ];

  return (
    <div>
      <div
        className={`${styles.frame_header} ${
          frameIndex === framesCount - 1 && !open && styles.bordered
        }`}
        onClick={() => setOpen(!open)}>
        <div className={styles.frame_title}>
          <span>
            {open ? (
              <MinusIcon height="15px" width="15px" />
            ) : (
              <PlusIcon height="15px" width="15px" />
            )}
          </span>
          <h3>Карточка {frameIndex + 1}</h3>
        </div>

        {frameIndex !== 0 && (
          <span onClick={() => remove(frameIndex)} className={styles.frame_bin}>
            <span></span>
            <i></i>
          </span>
        )}
      </div>
      {open && (
        <div className={styles.frame_content}>
          <div className={styles.input_field}>
            <FormField
              labelTitle={'Заголовок'}
              name={`stories.${storyIndex}.storyFrames.${frameIndex}.title`}
              type={'text'}
              errors={errors}
              touched={touched}
            />
          </div>
          <div className={styles.row}>
            <div className={styles.input_field}>
              <FormField
                labelTitle={'Текст'}
                name={`stories.${storyIndex}.storyFrames.${frameIndex}.text`}
                as={'textarea'}
                errors={errors}
                touched={touched}
              />
            </div>
            <FormField
              labelTitle={'Цвет текста'}
              name={`stories.${storyIndex}.storyFrames.${frameIndex}.textColor`}
              component={ColorPicker}
              errors={errors}
              touched={touched}
            />
          </div>
          <div className={styles.input_field}>
            <FormField
              labelTitle={'Картинка'}
              name={`stories.${storyIndex}.storyFrames.${frameIndex}.pictureUrl`}
              component={UploadImage}
              errors={errors}
              touched={touched}
            />
          </div>

          <div className={styles.input_field}>
            <FormField
              labelTitle={'Градиент'}
              name={`stories.${storyIndex}.storyFrames.${frameIndex}.gradient`}
              as="select"
              options={gradientOptions}
              errors={errors}
              touched={touched}
            />
          </div>

          <div role="group" aria-labelledby="my-radio-group">
            <div className={styles.row}>
              <label>
                <FormField
                  labelTitle="Кнопка"
                  type="radio"
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.visibleLinkOrButtonOrNone`}
                  value="BUTTON"
                  checked={frameJson.visibleLinkOrButtonOrNone === 'BUTTON'}
                  errors={errors}
                  touched={touched}
                />
              </label>

              <label>
                <FormField
                  labelTitle="Ссылка"
                  type="radio"
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.visibleLinkOrButtonOrNone`}
                  value="LINK"
                  checked={frameJson.visibleLinkOrButtonOrNone === 'LINK'}
                  errors={errors}
                  touched={touched}
                />
              </label>

              <label>
                <FormField
                  labelTitle="Ничего"
                  type="radio"
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.visibleLinkOrButtonOrNone`}
                  value="NONE"
                  checked={frameJson.visibleLinkOrButtonOrNone === 'NONE'}
                  errors={errors}
                  touched={touched}
                />
              </label>
            </div>
          </div>

          {frameJson.visibleLinkOrButtonOrNone === 'BUTTON' && (
            <>
              <div className={styles.row}>
                <div className={styles.input_field}>
                  <FormField
                    labelTitle={'Текст'}
                    name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonText`}
                    as={'textarea'}
                    errors={errors}
                    touched={touched}
                  />
                </div>
                <FormField
                  labelTitle={'Цвет текста'}
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonTextColor`}
                  component={ColorPicker}
                  errors={errors}
                  touched={touched}
                />
              </div>
              <FormField
                labelTitle={'Цвет кнопки'}
                name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonBackgroundColor`}
                component={ColorPicker}
                errors={errors}
                touched={touched}
              />
              <div className={styles.input_field}>
                <FormField
                  labelTitle={'Ссылка'}
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonUrl`}
                  type="text"
                  errors={errors}
                  touched={touched}
                />
              </div>
            </>
          )}
          {frameJson.visibleLinkOrButtonOrNone === 'LINK' && (
            <>
              <div className={styles.input_field}>
                <FormField
                  labelTitle={'Текст'}
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.linkText`}
                  type="text"
                  errors={errors}
                  touched={touched}
                />
              </div>
              <div className={styles.input_field}>
                <FormField
                  labelTitle={'Ссылка'}
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.linkUrl`}
                  type="text"
                  errors={errors}
                  touched={touched}
                />
              </div>
            </>
          )}
        </div>
      )}
    </div>
  );
};

export default FrameFields;
