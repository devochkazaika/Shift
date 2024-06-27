import React from 'react';

import { gradientOptions } from '../../../utils/constants/gradient';
import ColorPicker from '../../ColorPicker';
import FormField from '../../FormField';
import UploadImage from '../../UploadImage';
import Bin from '../../ui/Bin';

import { ReactComponent as MinusIcon } from '../../../assets/icons/minus.svg';
import { ReactComponent as PlusIcon } from '../../../assets/icons/plus.svg';

import styles from './StoryFormParts.module.scss';

const FrameFields = ({ storyIndex, frameIndex, frameJson, framesCount, remove, ...props }) => {
  const [open, setOpen] = React.useState(true);

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
              className="title"
              labelTitle={'Заголовок'}
              name={`stories.${storyIndex}.storyFrames.${frameIndex}.title`}
              as={'textarea'}
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
              <label htmlFor="ButtonIntarectiveType">
                <FormField
                  labelTitle="Кнопка"
                  id="ButtonIntarectiveType"
                  type="radio"
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.visibleLinkOrButtonOrNone`}
                  value="BUTTON"
                  checked={frameJson.visibleLinkOrButtonOrNone === 'BUTTON'}
                  {...props}
                />
              </label>

              <label htmlFor="LinkIntarectiveType">
                <FormField
                  labelTitle="Ссылка"
                  id="LinkIntarectiveType"
                  type="radio"
                  name={`stories.${storyIndex}.storyFrames.${frameIndex}.visibleLinkOrButtonOrNone`}
                  value="LINK"
                  checked={frameJson.visibleLinkOrButtonOrNone === 'LINK'}
                  {...props}
                />
              </label>

              <label htmlFor="NonIntarectiveType">
                <FormField
                  labelTitle="Ничего"
                  id="NonIntarectiveType"
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
        </div>
      )}
    </div>
  );
};

export default FrameFields;
