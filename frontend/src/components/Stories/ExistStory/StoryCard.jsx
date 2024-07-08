import React, { useState, useEffect } from 'react';
import StoryFrame from './StoryFrame';
import { FieldArray, Form, Field, Formik } from 'formik';
import FormField from "../../FormField";
import { gradientOptions } from './../../../utils/constants/gradient';
import { ReactComponent as ArrowIcon } from '../../../assets/icons/arrow-up.svg';
import { ReactComponent as DragIcon } from '../../../assets/icons/drag.svg';
import Button from '../../ui/Button';
import ColorPicker from './../../ColorPicker/index';
import { deleteFrame, updateStory, updateFrameOrder } from './../../../api/stories';
import UploadImage from './../../UploadImage/index';
import axios from 'axios';
import { storyPanelValidationSchema } from './../../../utils/helpers/validation';
import AddFrame from './AddFrame';

const StoryCard = ({ storyIndex, story, platform }) => {
  const [frames, setFrames] = useState(story.storyFrames);
  const [imageLoaded, setImageLoaded] = useState(false);
  const [initialImage, setInitialImage] = useState(null);

  useEffect(() => {
    const fetchImage = async () => {
      try {
        const response = await axios.get(`http://localhost:8080${story.previewUrl}`, { responseType: 'arraybuffer' });
        const blob = new Blob([response.data], { type: response.headers['content-type'] });
        const file = new File([blob], "initial_image.jpg", { type: blob.type });
        setInitialImage(file);
      } catch (error) {
        console.error('Error fetching the image:', error);
      }
    };

    if (!imageLoaded) {
      fetchImage();
      setImageLoaded(true);
    }

    if (story && story.storyFrames) {
      setFrames(story.storyFrames);
    }
  }, [story, imageLoaded]);

  useEffect(() => {
    const list = document.getElementById('draggable-list');

    const handleDragStart = (e) => {
      if (e.target.tagName === 'LI') {
        e.target.classList.add('dragging');
      }
    };

    const handleDragEnd = async (e) => {
      if (e.target.tagName === 'LI') {
        e.target.classList.remove('dragging');
        const newOrder = Array.from(list.children).map((child, index) => ({
          id: child.id,
          order: index
        }));
        
        let arr = [];
        frames.forEach((frame, index) => {
          if (frame.id !== newOrder[index].id) {
            arr.push(frame.id);
          }
        });
        
        if (arr.length) {
          // Swap cards
          try {
            await updateFrameOrder(story, platform, arr[0], arr[1]);
            setFrames(frames);
          } catch (error) {
            console.error('Error updating frame order:', error);
          }
        }
      }
    };

    const handleDragOver = (e) => {
      e.preventDefault();
      const afterElement = getDragAfterElement(list, e.clientY);
      const draggingElement = document.querySelector('.dragging');
      if (afterElement == null) {
        list.appendChild(draggingElement);
      } else {
        list.insertBefore(draggingElement, afterElement);
      }
    };

    const getDragAfterElement = (list, y) => {
      const draggableElements = [...list.querySelectorAll('.draggable:not(.dragging)')];

      return draggableElements.reduce((closest, child) => {
        const box = child.getBoundingClientRect();
        const offset = y - box.top - box.height / 2;
        if (offset < 0 && offset > closest.offset) {
          return { offset: offset, element: child };
        } else {
          return closest;
        }
      }, { offset: Number.NEGATIVE_INFINITY }).element;
    };

    list.addEventListener('dragover', handleDragOver);

    const draggables = list.querySelectorAll('li');
    draggables.forEach(draggable => {
      draggable.addEventListener('dragstart', handleDragStart);
      draggable.addEventListener('dragend', handleDragEnd);
    });

    return () => {
      draggables.forEach(draggable => {
        draggable.removeEventListener('dragstart', handleDragStart);
        draggable.removeEventListener('dragend', handleDragEnd);
      });
      list.removeEventListener('dragover', handleDragOver);
    };
  }, [frames]);

  const handleOnSubmit = async (story, frame, platform) => {
    const success = await deleteFrame(story, frame, platform);
    if (success) {
      setFrames(prevFrames => prevFrames.filter(item => item.id !== frame.id));
    }
  };

  return (
    <div>
      <div>
        <Formik
          enableReinitialize
          validationSchema={storyPanelValidationSchema}
          initialValues={{
            previewTitle: story.previewTitle,
            previewTitleColor: story.previewTitleColor,
            previewGradient: story.previewGradient,
            previewUrl: initialImage
          }}
        >
          {({ values, handleChange }) => (
            <Form>
              <FieldArray name="frames">
                {() => (
                  <div className="row" style={{ display: "flex", alignItems: "center" }}>
                    <div style={{ width: "70%" }}>
                      <div className="frame" style={{ marginBottom: "10px" }}>
                        <h3 style={{ paddingRight: "10px" }}>Заголовок</h3>
                        <div style={{ width: "100%" }}>
                          <Field
                            name="previewTitle"
                            as={FormField}
                            type="text"
                            value={values.previewTitle}
                            onChange={handleChange}
                          />
                        </div>
                      </div>
                      <FormField
                        name="previewTitleColor"
                        labelTitle="Цвет заголовка"
                        value={values.previewTitleColor}
                        component={ColorPicker}
                        onChange={handleChange}
                      />
                      <div className="frame">
                        <div>
                          <FormField
                            name="previewGradient"
                            labelTitle="Градиент"
                            value={values.previewGradient}
                            onChange={handleChange}
                            as="select"
                            options={gradientOptions}
                          />
                        </div>
                      </div>
                    </div>
                    <div style={{ width: "30%", marginLeft: "auto", float: "right" }}>
                      <div className="input_field">
                        <FormField
                          name="previewUrl"
                          component={UploadImage}
                        />
                      </div>
                      <Button
                        handleOnClick={() => updateStory(story, values, platform)}
                        text="Изменить"
                        type="button"
                        color="green"
                        icon={<ArrowIcon width="12px" height="12px" />}
                      />
                    </div>
                  </div>
                )}
              </FieldArray>
            </Form>
          )}
        </Formik>
      </div>
      <div>
        <h3>Story Frames:</h3>
        <ul id="draggable-list">
          {frames.map((value, index) => (
            <li id={value.id} className="listFrame draggable" key={index} draggable="true">
              <details className="item-card">
                <summary className="item-card__summary">
                  <p className="item-card__title">
                    <DragIcon style={{ cursor: 'grab', marginRight: '8px', width: '20px', height: '20px'}} /> {/* Иконка для перемещения */}
                    {value.title}
                  </p>
                  <div className="item-card__buttons">
                    <div className="item-card__button--delete">
                      <Button
                        text="Удалить"
                        type="button"
                        color="red"
                        icon={<ArrowIcon width="12px" height="12px" />}
                        handleOnClick={() => handleOnSubmit(story, value, platform)}
                      />
                    </div>
                  </div>
                </summary>
                <div className="item-card__content">
                  <StoryFrame
                    key={index}
                    frame={value}
                    frameIndex={index}
                    storyIndex={storyIndex}
                    story={story}
                    platform={platform}
                  />
                </div>
              </details>
            </li>
          ))}
          </ul>
          <ul>
          <li className="listFrame addFrame">
            <details className="item-card item-card__add">
              <summary className="item-card__summary">
                <p className="item-card__title">Добавить карточку</p>
              </summary>
              <div className="item-card__content">
                <AddFrame
                  setFrames={setFrames}
                  frames={frames}
                  storyIndex={storyIndex}
                  story={story}
                  platform={platform}
                />
              </div>
            </details>
          </li>
        </ul>
      </div>
    </div>
  );
};

export default StoryCard;
