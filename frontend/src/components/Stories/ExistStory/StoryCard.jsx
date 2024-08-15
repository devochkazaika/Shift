import React, { useState, useEffect, useRef } from "react";
import StoryFrame from "./StoryFrame";
import { FieldArray, Form, Formik } from "formik";
import FormField from "../../FormField";
import { gradientOptions } from "../../../utils/constants/gradient";
import { storyPanelValidationSchema } from "../../../utils/helpers/validation";
import Button from "../../ui/Button";
import { ReactComponent as DragIcon } from "../../../assets/icons/drag.svg";
import ColorPicker from "../../ColorPicker";
import {
  deleteFrame,
  updateStory,
  updateFrameOrder,
  fetchImage,
} from "../../../api/stories";
import UploadImage from "../../UploadImage";
import AddFrame from "./AddFrame";
import styles from "./StoryCard.module.scss";

const StoryCard = ({ storyIndex, story, platform, changeable, ...props }) => {
  const [frames, setFrames] = useState(story.storyFrames || []);
  const [initialImage, setInitialImage] = useState(null);
  const draggableListRef = useRef(null);

  useEffect(() => {
    if (!initialImage && story.previewUrl) {
      fetchImage(story.previewUrl, setInitialImage);
    }
    if (story && story.storyFrames) {
      setFrames(story.storyFrames);
    }
  }, [story, initialImage]);

  useEffect(() => {
    const draggableList = draggableListRef.current;
    let initialOrder = frames.map((frame) => frame.id);

    const handleDragStart = (e) => {
      if (e.target.tagName === "LI") {
        e.target.classList.add("dragging");
      }
    };

    const handleDragEnd = async (e) => {
      if (e.target.tagName === "LI") {
        e.target.classList.remove("dragging");
        const newOrder = Array.from(draggableList.children).map(
          (child) => child.id
        );

        let changedIds = [];
        for (let i = 0; i < newOrder.length; i++) {
          if (newOrder[i] !== initialOrder[i]) {
            changedIds.push(initialOrder[i], newOrder[i]);
            break;
          }
        }

        if (changedIds.length === 2) {
          try {
            await updateFrameOrder(
              story,
              platform,
              changedIds[0],
              changedIds[1]
            );
            setFrames((prevFrames) =>
              prevFrames.sort(
                (a, b) => newOrder.indexOf(a.id) - newOrder.indexOf(b.id)
              )
            );
          } catch (error) {
            console.error("Ошибка при обновлении порядка фреймов:", error);
          }
        }
      }
    };

    const handleDragOver = (e) => {
      e.preventDefault();
      const afterElement = getDragAfterElement(draggableList, e.clientY);
      const draggingElement = draggableList.querySelector(".dragging");
      if (afterElement == null) {
        draggableList.appendChild(draggingElement);
      } else {
        draggableList.insertBefore(draggingElement, afterElement);
      }
    };

    const getDragAfterElement = (list, clientY) => {
      const draggableElements = [
        ...list.querySelectorAll(".draggable:not(.dragging)"),
      ];
      return draggableElements.reduce(
        (closest, child) => {
          const box = child.getBoundingClientRect();
          const offset = clientY - box.top - box.height / 2;
          if (offset < 0 && offset > closest.offset) {
            return { offset: offset, element: child };
          } else {
            return closest;
          }
        },
        { offset: Number.NEGATIVE_INFINITY }
      ).element;
    };

    draggableList.addEventListener("dragover", handleDragOver);

    const draggables = draggableList.querySelectorAll("li");
    draggables.forEach((draggable) => {
      draggable.addEventListener("dragstart", handleDragStart);
      draggable.addEventListener("dragend", handleDragEnd);
    });

    return () => {
      draggables.forEach((draggable) => {
        draggable.removeEventListener("dragstart", handleDragStart);
        draggable.removeEventListener("dragend", handleDragEnd);
      });
      draggableList.removeEventListener("dragover", handleDragOver);
    };
  }, [frames, story, platform]);

  const handleOnSubmit = async (frame) => {
    const success = await deleteFrame(story, frame, platform);
    if (success) {
      setFrames((prevFrames) =>
        prevFrames.filter((item) => item.id !== frame.id)
      );
    }
  };

  return (
    <div>
      <Formik
        enableReinitialize
        validationSchema={storyPanelValidationSchema(storyIndex)}
        initialValues={{
          previewTitle: story.previewTitle,
          previewTitleColor: story.previewTitleColor,
          previewGradient: story.previewGradient,
          [`previewUrl_${storyIndex}`]: initialImage,
        }}
        onSubmit={(values) => {
          updateStory(story, storyIndex, values, platform);
        }}
      >
        {({ values, handleChange }) => (
          <Form>
            <FieldArray name="frames">
              {() => (
                <div className={`row ${styles.wrapper}`}>
                  <div className={styles.heading_input_size}>
                    <div>
                      <h3>Превью</h3>
                      <FormField
                        labelTitle="Заголовок"
                        name="previewTitle"
                        type="text"
                        value={values.previewTitle}
                        onChange={handleChange}
                        {...props}
                      />
                    </div>
                    <FormField
                      name="previewTitleColor"
                      labelTitle="Цвет заголовка"
                      value={values.previewTitleColor}
                      component={ColorPicker}
                      onChange={handleChange}
                      {...props}
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
                          {...props}
                        />
                      </div>
                    </div>
                  </div>
                  <div className={styles.gradient_input_area}>
                    <div className="input_field">
                      <FormField
                        name={`previewUrl_${storyIndex}`}
                        component={UploadImage}
                        type="file"
                      />
                    </div>
                    {!changeable && (
                      <Button text="Изменить" type="submit" color="green" />
                    )}
                  </div>
                </div>
              )}
            </FieldArray>
          </Form>
        )}
      </Formik>
      <div>
        <h3>Карточки</h3>
        <ul ref={draggableListRef} id={`draggable-list-${storyIndex}`}>
          {frames.map((value, index) => (
            <li
              id={value.id}
              className="listFrame draggable"
              key={index}
              draggable="true"
            >
              <details>
                <summary
                  style={{display: "flex", justifyContent: "center", height: "50px"}}
                >
                  <DragIcon
                    style={{ marginTop: "5px" }}
                    className={styles.drag_icon}
                  />
                  <p
                    style={{
                      margin: "0",
                      paddingTop: "2px"
                    }}
                  >
                    {value.title}
                  </p>
                  {!changeable && (
                    <div>
                      <div>
                        <Button
                          text="Удалить"
                          type="button"
                          color="red"
                          handleOnClick={() => handleOnSubmit(value)}
                        />
                      </div>
                    </div>
                  )}
                </summary>
                <div>
                  <StoryFrame
                    key={index}
                    changeable={changeable}
                    frame={value}
                    frameIndex={index}
                    storyIndex={storyIndex}
                    story={story}
                    platform={platform}
                    {...props}
                  />
                </div>
              </details>
            </li>
          ))}
        </ul>
        {!changeable && (
          <div>
            <details id="addFrame">
              <summary>Добавить карточку</summary>
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
        )}
      </div>
    </div>
  );
};

export default StoryCard;
