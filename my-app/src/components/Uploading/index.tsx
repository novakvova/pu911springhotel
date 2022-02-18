import "cropperjs/dist/cropper.min.css";
import * as React from "react";
import { Col, Row } from "antd";
import CropperModal from '../common/CropperModal';
import http, {urlBackend} from '../../http_common';


const UploadingPage: React.FC = () => {
  const [images, setImages] = React.useState<Array<string>>([]); 

  const handleSelected = async (base64: string) => {
    const imgName = await http.post<string>("upload", {base64: base64});
    console.log(imgName.data);
    
    setImages([...images,urlBackend+"files/"+imgName.data]);
  };

  const dataImages = images.map((item, key) => {
    return (
      <Col md={4} key={key}>
        <div>
          <img src={item} alt="images" width="100%" />
        </div>
      </Col>
    );
  });
  // <span id=""></span>
  // $("#header").html(""); //innerHtml
  return (
    <>
      <h1>Загрузка фоток</h1>
      <Row gutter={[8, 8]}>
        {dataImages}
        <Col md={4}>
          <div>
            <CropperModal onSelected={handleSelected} aspectRation={16 / 9} />
          </div>
        </Col>
      </Row>
    </>
  );
};

export default UploadingPage;
