import { React, useEffect, useState } from "react";
import { getUnApprovedChangedStoriesByBank } from "../../../api/stories";
import styles from "../../../styles/StoryPanelStyle.module.scss";
// import Button from "../../ui/Button";
import PreviewStory from "../ExistStory/PreviewStory";
import Button from "../../ui/Button";

const PreviewChangeRequest = ({bankId, platform}) => {
  // const [currentPage, setCurrentPage] = React.useState(1);
  const [storiesArray, setStoriesArray] = useState([]);

  // const approve = (existStory, changeStory) => {
  //
  // }

  useEffect(() => {
    const fetchDataAsync = async () => {
      try {
        const data = await getUnApprovedChangedStoriesByBank(bankId, platform);
        if (data == null) {
          setStoriesArray([]);
        } else {
          setStoriesArray(data);
          // setCurrentPage(1);
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchDataAsync();
  }, [bankId, platform]);

  return (
    <div>
      <h1>Запросы на изменение Превью историй</h1>

      {storiesArray.length > 0 ? (
        <ul className="stories">
          <h2>{platform}</h2>
          {storiesArray.map((story, index) => (
            <>
              {story["changes"].map((storyElem, indexChange) => (
                <li className={styles["listFrame"]} key={indexChange}>
                  <details>
                    <summary>
                      <p>{story["story"].previewTitle}</p>
                      <div>
                        <div className="row">
                          <Button
                            text="Принять"
                            type="button"
                            color="green"
                            // handleOnClick={() => approve(storyElem, platform)}
                          />
                        </div>
                      </div>
                    </summary>
                    <div>
                      <>
                        <PreviewStory
                          story={storyElem["storyPresentation"]}
                          storyIndex={index}
                          platform={platform}
                          changeable={true}
                        />
                        <PreviewStory
                          story={story["story"]}
                          storyIndex={index}
                          platform={platform}
                          changeable={true}
                        />
                      </>
                    </div>
                  </details>
                </li>
              ))}
            </>
          ))}

        </ul>
      ) : (
        <h2>Пока нет историй</h2>
      )}
    </div>
  )
}

export default PreviewChangeRequest;