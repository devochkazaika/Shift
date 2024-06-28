import axios from "axios";

const fetchData = async (bankId, platform) => {
  try {
    const response = await axios.get('http://localhost:8080/stories/bank/info/getJson/?bankId='+bankId+'&platform='+platform, {
      headers: {
        // 'Content-Type': 'multipart/form-data', // Не нужен для GET-запроса
      },
      responseType: 'json' // Ожидаем JSON-ответ
    });
    // console.log(response.data); // Данные находятся в свойстве data
    return response.data;
  } catch (error) {
    console.error('Error fetching data:', error);
    return null;
  }
};

export default fetchData;