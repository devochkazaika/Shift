import React from 'react';

import FormField from '../FormField';
import ColorPicker from '../ColorPicker';
import UploadImage from '../UploadImage';
import Bin from '../ui/Bin';

import { ReactComponent as MinusIcon } from '../../assets/icons/minus.svg';
import { ReactComponent as PlusIcon } from '../../assets/icons/plus.svg';

import styles from './StoryFormParts.module.scss';

const FrameFields = ({ storyIndex, frameIndex, frameJson, framesCount, remove, ...props }) => {
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

        {frameIndex !== 0 && <Bin handleOnClick={() => remove(frameIndex)} />}
      </div>
      {open && (
        <div className={styles.frame_content}>
          <div className="input_field">
            <FormField
              labelTitle={'Заголовок'}
              name={`stories.${storyIndex}.storyFrames.${frameIndex}.title`}
              type={'text'}
              {...props}
            />
          </div>
          <div className="row">
            <div className="input_field">
              <FormField
                labelTitle={'Текст'}
                name={`stories.${storyIndex}.storyFrames.${frameIndex}.text`}
                as={'textarea'}
                {...props}
              />
            </div>
            <FormField
              labelTitle={'Цвет текста'}
              name={`stories.${storyIndex}.storyFrames.${frameIndex}.textColor`}
              component={ColorPicker}
              {...props}
            />
          </div>
          <div className="input_field">
            <FormField
              labelTitle={'Картинка'}
              name={`stories.${storyIndex}.storyFrames.${frameIndex}.pictureUrl`}
              component={UploadImage}
              {...props}
            />
          </div>

          <div className="input_field">
            <FormField
              labelTitle={'Градиент'}
              name={`stories.${storyIndex}.storyFrames.${frameIndex}.gradient`}
              as="select"
              options={gradientOptions}
              {...props}
            />
          </div>

          <div role="group" aria-labelledby="my-radio-group">
            <div className="row">
              <label>
                <FormField
                  labelTitle="Кнопка"
                  type="radio"
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.visibleLinkOrButtonOrNone`}
                  value="BUTTON"
                  checked={frameJson.visibleLinkOrButtonOrNone === 'BUTTON'}
                  {...props}
                />
              </label>

              <label>
                <FormField
                  labelTitle="Ссылка"
                  type="radio"
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.visibleLinkOrButtonOrNone`}
                  value="LINK"
                  checked={frameJson.visibleLinkOrButtonOrNone === 'LINK'}
                  {...props}
                />
              </label>

              <label>
                <FormField
                  labelTitle="Ничего"
                  type="radio"
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.visibleLinkOrButtonOrNone`}
                  value="NONE"
                  checked={frameJson.visibleLinkOrButtonOrNone === 'NONE'}
                  {...props}
                />
              </label>
            </div>
          </div>

          {frameJson.visibleLinkOrButtonOrNone === 'BUTTON' && (
            <>
              <div className="row">
                <div className="input_field">
                  <FormField
                    labelTitle={'Текст'}
                    name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonText`}
                    as={'textarea'}
                    {...props}
                  />
                </div>
                <FormField
                  labelTitle={'Цвет текста'}
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonTextColor`}
                  component={ColorPicker}
                  {...props}
                />
              </div>
              <FormField
                labelTitle={'Цвет кнопки'}
                name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonBackgroundColor`}
                component={ColorPicker}
                {...props}
              />
              <div className="input_field">
                <FormField
                  labelTitle={'Ссылка'}
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.buttonUrl`}
                  type="text"
                  {...props}
                />
              </div>
            </>
          )}
          {frameJson.visibleLinkOrButtonOrNone === 'LINK' && (
            <>
              <div className="input_field">
                <FormField
                  labelTitle={'Текст'}
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.linkText`}
                  type="text"
                  {...props}
                />
              </div>
              <div className="input_field">
                <FormField
                  labelTitle={'Ссылка'}
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.linkUrl`}
                  type="text"
                  {...props}
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
