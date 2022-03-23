import "cropperjs/dist/cropper.min.css";
import * as React from "react";
import { Modal, Button, Col, Row, Alert } from "antd";
import Cropper from "cropperjs";
import { useGoogleReCaptcha } from "react-google-recaptcha-v3";
import http from '../../http_common';
// import { margin } from "@mui/system";


const HomePage: React.FC = () => {
  const [visible, setVisible] = React.useState(false);
  const { executeRecaptcha } = useGoogleReCaptcha();
  const [error, setError] = React.useState<string>("");

  const imgRef = React.useRef<HTMLImageElement>(null);
  const prevRef = React.useRef<HTMLImageElement>(null);
  const [cropperObj, setCropperObj] = React.useState<Cropper>();
  const [imageView, setImageView] = React.useState<string>(
    "https://www.securityindustry.org/wp-content/uploads/sites/3/2018/05/noimage.png"
  );

  //вибір файла
  const handleChangeFile = async (e: any) => {
    const file = (e.target.files as FileList)[0];
    if (file) {
      const url = URL.createObjectURL(file);

      await setVisible(true);
      console.log("Image ref ", imgRef);
      let cropper = cropperObj;
      if (!cropper) {
        cropper = new Cropper(imgRef.current as HTMLImageElement, {
          aspectRatio: 1 / 1,
          viewMode: 1,
          preview: prevRef.current as HTMLImageElement,
        });
      }
      cropper.replace(url);
      e.target.value = "";
      setCropperObj(cropper);
    }
  };

  const handleOk = async () => {
    const base64 = cropperObj?.getCroppedCanvas().toDataURL() as string;
    console.log("Cropper data: ", base64);
    await setImageView(base64);
    setVisible(false);
  };

  //send data server and recaptcha
  const handleSend = async () => {
    if(!executeRecaptcha) {
      setError("Помилка перевірки Captcha");
      return;
    }
    const recaptchaToken = await executeRecaptcha();
    console.log("token = ", recaptchaToken);

    const resp = await http.post("api/public/register", {
      email: "slavik@gmail.com",
      password: "123456",
      fullName: "Мельник Іван",
      reCaptchaToken: recaptchaToken
    });
  }
  


  return (
    <>
      <h1>Головна сторінка</h1>

      <Button type="primary" onClick={handleSend}>
        Обрати фото
      </Button>
      {!!error &&  <Alert message={error} type="error" />}

      <label htmlFor="uploading">
        <img src={imageView} alt="" width="250" style={{ cursor: "pointer" }} />
      </label>

      <input
        id="uploading"
        style={{ display: "none" }}
        type="file"
        onChange={handleChangeFile}
      />

      <Modal
        title="Modal 1000px width"
        centered
        visible={visible}
        onOk={handleOk}
        onCancel={() => setVisible(false)}
        width={1000}
      >
        <Row gutter={[8, 8]}>
          <Col md={18} xs={24}>
            <div>
              <img
                src="https://vovalohika.tk/images/1200_gntox1zw.ipw.jpeg"
                width="100%"
                style={{ maxHeight: "600px" }}
                ref={imgRef}
              />
            </div>
          </Col>
          <Col md={6} xs={24}>
            <div
              ref={prevRef}
              style={{
                height: "150px",
                border: "1px solid silver",
                overflow: "hidden",
              }}
            ></div>
          </Col>
        </Row>
      </Modal>
    </>
  );
};

export default HomePage;
