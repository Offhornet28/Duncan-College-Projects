from flask import Flask, request, send_from_directory, jsonify, render_template
from werkzeug.utils import secure_filename
import os
import subprocess
import glob

# new flask instance
app = Flask(__name__)

# dir. paths
BASE_DIR = os.path.dirname(os.path.abspath(__file__))
UPLOAD_FOLDER = os.path.join(BASE_DIR, 'uploads')
OUTPUT_FOLDER = os.path.join(BASE_DIR, 'PDF')
ALLOWED_EXTENSIONS = {'txt'}

app.config['OUTPUT_FOLDER'] = OUTPUT_FOLDER
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


# check file ext.
def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


def clear_pdf_folder():
    files = glob.glob(os.path.join(app.config['OUTPUT_FOLDER'], '*.pdf'))
    for f in files:
        os.remove(f)


# upload file and run command for pdf
def process_file(filepath):
    filename = os.path.basename(filepath)
    output_pdf = f"{os.path.splitext(filename)[0]}.pdf"
    subprocess.run("./run_backend.sh", shell=True, check=True)
    return output_pdf


# route for website
@app.route('/')
def index():
    return render_template('website.html')


# route for file uploads
@app.route('/uploads', methods=['POST'])
def upload_file():
    if 'file' not in request.files:
        return jsonify({"error": "No file part"}), 400
    file = request.files['file']
    if file.filename == '':
        return jsonify({"error": "No selected file"}), 400
    if file and allowed_file(file.filename):
        clear_pdf_folder()
        filename = secure_filename(file.filename)
        filepath = os.path.join(app.config['UPLOAD_FOLDER'], filename)
        file.save(filepath)
        output_pdf = process_file(filepath)
        return jsonify({"message": "File uploaded and processed successfully", "pdf": output_pdf}), 200
    else:
        return jsonify({"error": "Invalid file type"}), 400


# route to get latest pdf
@app.route('/get_latest_pdf')
def get_latest_pdf():
    try:
        pdf_files = glob.glob(os.path.join(app.config['OUTPUT_FOLDER'], '*.pdf'))
        if pdf_files:
            latest_pdf = os.path.basename(sorted(pdf_files, key=os.path.getmtime)[-1])
            return jsonify({"pdf": latest_pdf}), 200
        else:
            return jsonify({"error": "No PDF found"}), 404
    except Exception as e:
        return jsonify({"error": str(e)}), 500


# route for sending pdf files from output directory
@app.route('/PDF/<filename>')
def retrieve_pdf(filename):
    try:
        return send_from_directory(app.config['OUTPUT_FOLDER'], filename)
    except Exception as e:  # debugging
        print(f"Failed to send file due to: {str(e)}")
        return jsonify({"error": "Internal Server Error", "message": str(e)}), 500


if __name__ == '__main__':
    app.run(debug=True)
