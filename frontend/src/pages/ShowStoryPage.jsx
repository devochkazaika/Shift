import React, { useEffect, useState } from "react";
import { getStories } from "../api/stories";
import { Form, Formik } from "formik";
import CommonForm from "../components/Stories/CommonForm";
import { initialStoryValues } from "../utils/constants/initialValues";
import ShowStory from "../components/Stories/ShowStory";

const ShowStoryPage = () => {
    const [bankId, setBankId] = useState("absolutbank");
    const [platform, setPlatform] = useState("ALL PLATFORMS");
    const [storiesArray, setStoriesArray] = useState([]);
  
    useEffect(() => {
      const fetchDataAsync = async () => {
        try {
          const data = await getStories(bankId, platform);
          setStoriesArray(data);
        } catch (error) {
          console.error("Error fetching data:", error);
          setStoriesArray([]);
        }
      };
  
      fetchDataAsync();
    }, [bankId, platform]);
  
    return (
      <>
        <h1>Stories</h1>
  
        <div className="stories">
          <Formik enableReinitialize initialValues={initialStoryValues}>
            {(props) => (
              <Form>
                <CommonForm
                  setBankId={setBankId}
                  setPlatform={setPlatform}
                  {...props}
                />
              </Form>
            )}
          </Formik>
        </div>
        <div>
            {storiesArray.length > 0 ? (
            <ShowStory stories={storiesArray[0]} />
            ) : (
              <h2>Пока нет историй</h2>
            )}
        </div>
      </>
    );
  }
  
  export default ShowStoryPage;