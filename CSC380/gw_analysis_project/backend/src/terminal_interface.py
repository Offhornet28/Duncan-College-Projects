import os
os.environ['OPENBLAS_NUM_THREADS'] = '8'
from graph_creator import read_original_data, read_2023_data, read_dr_barber_data, plot_data
from datetime import date
from pdf_creator import create_pdf
from fpdf import FPDF
import tempfile


def create_graphs(data, header_units, temp_directory):
    plot_data(data, header_units, temp_directory)
    print("")


def prepare_to_create_pdf(data, header_units, lat_long_coordinates, temp_directory, file_format, input_file_name):
    string_list = []

    # # Name
    # name = input("Enter your name: ")
    # string_list.append(name)
    #
    # # Doc Title
    # title = input("Enter title: ")
    # string_list.append(title)
    #
    # # Doc Abstract
    # abstract = input("Enter abstract: ")
    # string_list.append(abstract)

    # Date
    today = date.today()
    today_string = today.strftime("%m/%d/%Y")
    string_list.append(today_string)

    pdf = FPDF()
    create_pdf(pdf, 10, 200, 200, string_list, input_file_name, file_format, data, header_units, lat_long_coordinates,
               temp_directory)


def main():
    # Format A /Users/tinaj/Downloads/Tolten_Profile/T3_1800_12132020_Artemis_Rerun.txt
    # Format B /Users/tinaj/Downloads/NewYork_SUNYOswego_Formatted_v2/NewYork_SUNYOswego_231014_0837_Q2.txt
    # Format C /Users/tinaj/Downloads/SUO052100_101323_id2.txt
    # /Users/tinaj/Desktop/Test

    file_formats = [
        "read_dr_barber_data",  # Format C
        "read_2023_data",       # Format B
        "read_original_data",   # Format A
    ]

    # input_file_path = "/Users/tinaj/Downloads/SUO052100_101323_id2.txt" (Used by Justyce for testing)
    #input_file_path = "/home/zaugb/docker-backend/src/profile"
    input_file_path = "/src/profile"
    radiosonde_files = os.listdir(input_file_path)
    if len(radiosonde_files) == 0:
        print("Error: No files found in directory!")
    else:
        # Always get the first file
        input_file_name = input_file_path + "/" + radiosonde_files[0]
        file_name, _ = os.path.splitext(radiosonde_files[0])

        valid_format = None
        lat_long_coordinates = []

        for i, cur_format in enumerate(file_formats):
            try:
                if i == 0:
                    data, header_units = read_dr_barber_data(input_file_name)
                elif i == 1:
                    data, header_units = read_2023_data(input_file_name)
                else:
                    data, header_units = read_original_data(input_file_name)

                if cur_format != "read_2023_data":
                    lat_long_coordinates = list(zip(data["Lat."], data["Long."]))
                else:
                    lat_long_coordinates = list(zip(data["Long."], data["Lat."]))

                valid_format = cur_format
                print("Detected Format: " + cur_format)
                break
            except Exception as e:
                continue

        if valid_format is None:
            print("File not found or formatted properly. Check the path and content of the file or try another one.")
        else:
            # Have a temporary directory to hold the graphs for the current file
            temp_directory = tempfile.mkdtemp()

            run = True
            while run:
                """
                user_action = input("What would you like to do? \n\n"
                                    "(1) Create Customized Graph for PDF\n"
                                    "(2) Generate PDF\n"
                                    "Enter a number (1-2): ")

                print("")

                if user_action == "1":
                    create_graphs(data, header_units, temp_directory)
                elif user_action == "2":
                    prepare_to_create_pdf(data, header_units, lat_long_coordinates, temp_directory, valid_format,
                                          input_file_name)
                    for file in os.listdir(temp_directory):
                        file_path = os.path.join(temp_directory, file)
                        os.remove(file_path)
                    os.rmdir(temp_directory)
                    run = False
                else:
                    print("Invalid request!")
                """
                prepare_to_create_pdf(data, header_units, lat_long_coordinates, temp_directory, valid_format, file_name)
                for file in os.listdir(temp_directory):
                    file_path = os.path.join(temp_directory, file)
                    os.remove(file_path)
                os.rmdir(temp_directory)
                os.remove(input_file_name)
                run = False


if __name__ == "__main__":
    main()
