import React, { useState, useEffect } from 'react';
import StoryFrame from './StoryFrame';
import { FieldArray, Form, Formik } from 'formik';
import FormField from "../../FormField";
import { gradientOptions } from './../../../utils/constants/gradient';
import { ReactComponent as ArrowIcon } from '../../../assets/icons/arrow-up.svg';
import { ReactComponent as DragIcon } from '../../../assets/icons/drag.svg';
import Button from '../../ui/Button';
import ColorPicker from './../../ColorPicker/index';
import { deleteFrame, updateStory, updateFrameOrder, fetchImage } from './../../../api/stories';
import UploadImage from './../../UploadImage/index';
import { storyPanelValidationSchema } from './../../../utils/helpers/validation';
import AddFrame from './AddFrame';

const StoryCard = ({ storyIndex, story, platform }) => {
  const [frames, setFrames] = useState(story.storyFrames);
  const [initialImage, setInitialImage] = useState(null);

  useEffect(() => {
  
    if (!initialImage) {
      fetchImage(story.previewUrl, setInitialImage);
    }

    if (story && story.storyFrames) {
      setFrames(story.storyFrames);
    }
  }, [story, initialImage]);

  useEffect(() => {
    const list = document.getElementById('draggable-list');
    let initialOrder = frames.map((frame) => frame.id);
  
    const handleDragStart = (e) => {
      if (e.target.tagName === 'LI') {
        e.target.classList.add('dragging');
      }
    };
  
    const handleDragEnd = async (e) => {
      if (e.target.tagName === 'LI') {
        e.target.classList.remove('dragging');
        const newOrder = Array.from(list.children).map((child) => {
          if (initialOrder.includes(child.id)){
            return child.id
          }
        }
        );
  
        // Поиск изменённых карточек
        let changedIds = [];
        for (let i = 0; i < newOrder.length; i++) {
          // Тут костыль в будущем надо поменять
          if (newOrder[i] !== initialOrder[i] && newOrder[i] !== undefined) {
            changedIds.push(initialOrder[i]);
            changedIds.push(newOrder[i]);
            break;
          }
        }
  
        if (changedIds.length === 2) {
          try {
            await updateFrameOrder(story, platform, changedIds[0], changedIds[1]);
            setFrames((prevFrames) =>
              prevFrames.sort((a, b) => newOrder.indexOf(a.id) - newOrder.indexOf(b.id))
            );
          } catch (error) {
            console.error('Ошибка при обновлении порядка фреймов:', error);
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
  }, [frames, story, platform]);

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
                      <div>
                          <FormField
                            labelTitle = "Заголовок"
                            name="previewTitle"
                            type="text"
                            value={values.previewTitle}
                            onChange={handleChange}
                          />
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
              <details>
                <summary>
                  <p>
                    <DragIcon style={{ cursor: 'grab', marginRight: '8px', width: '20px', height: '20px'}} /> {/* Иконка для перемещения */}
                    {value.title}
                  </p>
                  <div>
                    <div>
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
                <div>
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
          <div>
            <details className='addFrame'>
              <summary>
                Добавить карточку
              </summary>
              <div>
                <AddFrame
                  setFrames={setFrames}
                  frames={frames}
                  storyIndex={storyIndex}
                  story={story}
                  platform={platform}
                />
              </div>
            </details>
          </div>
      </div>
    </div>
  );
};

export default StoryCard;
