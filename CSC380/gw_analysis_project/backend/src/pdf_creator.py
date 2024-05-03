import os
from graph_creator import *
import GDL
import subprocess
# import re
# import numpy


# Error Handling
class GDLError(Exception):
    def __init__(self, message):
        self.message = message

    def __str__(self):
        return self.message


def main_gdl(pdf, line_height, tropopause_altitude):
    # Initial Setup
    initial_directory = os.getcwd()
    # We have to change this to wherever the directory that holds the GDL code is.
    os.chdir('/src/pro')
    debug = False
    # debug code
    if debug:
        path = os.getcwd()
        print("current working directory: " + path + "\n")
    # running the gdl code
    GDL.script("gw_eclipse_run.pro")
    # This code is for in case the GDL-Python interface module cannot be imported/used
    #gdl_shell_command = "gdl gw_eclipse_run.pro"
    #subprocess.run(gdl_shell_command, shell=True)
    # Getting the results of the GDL code
    file_path = "/src/data/outfile.txt"
    file = open(file_path, 'r')
    raw_gdl_results = file.read()
    # Troposphere Variables
    tropo_z_bottom = 0.0
    tropo_z_top = 0.0
    tropo_horizontal_wavelength = 0.0
    tropo_vertical_wavelength = 0.0
    tropo_mean_phase_propagation_direction = 0.0
    tropo_upward_propagation_fraction = 0.0
    tropo_zonal_momentum_flux = 0.0
    tropo_meridional_momentum_flux = 0.0
    tropo_potential_energy = 0.0
    tropo_kinetic_energy = 0.0
    tropo_intrinsic_frequency = 0.0
    tropo_coriolis_parameter = 0.0
    # Stratosphere Variables
    strato_z_bottom = 0.0
    strato_z_top = 0.0
    strato_horizontal_wavelength = 0.0
    strato_vertical_wavelength = 0.0
    strato_mean_phase_propagation_direction = 0.0
    strato_upward_propagation_fraction = 0.0
    strato_zonal_momentum_flux = 0.0
    strato_meridional_momentum_flux = 0.0
    strato_potential_energy = 0.0
    strato_kinetic_energy = 0.0
    strato_intrinsic_frequency = 0.0
    strato_coriolis_parameter = 0.0
    # Output Flag (If not zero, throw an error)
    output_flag = 0
    print("")
    # Put results into Python
    line_number = 0
    for line in raw_gdl_results.split('\n'):
        if line_number == 1:
            print(line)
        elif line_number == 2:
            tropo_z_bottom = float(line)
        elif line_number == 3:
            tropo_z_top = float(line)
        elif line_number == 4:
            tropo_horizontal_wavelength = float(line)
        elif line_number == 5:
            tropo_vertical_wavelength = float(line)
        elif line_number == 6:
            tropo_mean_phase_propagation_direction = float(line)
        elif line_number == 7:
            tropo_upward_propagation_fraction = float(line)
        elif line_number == 8:
            tropo_zonal_momentum_flux = float(line)
        elif line_number == 9:
            tropo_meridional_momentum_flux = float(line)
        elif line_number == 10:
            tropo_potential_energy = float(line)
        elif line_number == 11:
            tropo_kinetic_energy = float(line)
        elif line_number == 12:
            tropo_intrinsic_frequency = float(line)
        elif line_number == 13:
            output_flag = float(line)
        elif line_number == 14:
            tropo_coriolis_parameter = float(line)
        elif line_number == 16:
            print(line)
        elif line_number == 17:
            strato_z_bottom = float(line)
        elif line_number == 18:
            strato_z_top = float(line)
        elif line_number == 19:
            strato_horizontal_wavelength = float(line)
        elif line_number == 20:
            strato_vertical_wavelength = float(line)
        elif line_number == 21:
            strato_mean_phase_propagation_direction = float(line)
        elif line_number == 22:
            strato_upward_propagation_fraction = float(line)
        elif line_number == 23:
            strato_zonal_momentum_flux = float(line)
        elif line_number == 24:
            strato_meridional_momentum_flux = float(line)
        elif line_number == 25:
            strato_potential_energy = float(line)
        elif line_number == 26:
            strato_kinetic_energy = float(line)
        elif line_number == 27:
            strato_intrinsic_frequency = float(line)
        elif line_number == 28:
            output_flag = float(line)
        elif line_number == 29:
            strato_coriolis_parameter = float(line)
        line_number = line_number + 1
    # Close and remove the file
    file.close()
    os.remove(file_path)
    os.chdir(initial_directory)
    # Check for errors
    output_check = tropo_z_bottom + tropo_z_top + tropo_horizontal_wavelength + tropo_vertical_wavelength + \
                   tropo_mean_phase_propagation_direction + tropo_upward_propagation_fraction + \
                   tropo_zonal_momentum_flux + tropo_meridional_momentum_flux + tropo_potential_energy + \
                   tropo_kinetic_energy + tropo_intrinsic_frequency + tropo_coriolis_parameter + strato_z_bottom + \
                   strato_z_top + strato_horizontal_wavelength + strato_vertical_wavelength + \
                   strato_mean_phase_propagation_direction + strato_upward_propagation_fraction + \
                   strato_zonal_momentum_flux + strato_meridional_momentum_flux + strato_potential_energy + \
                   strato_kinetic_energy + strato_intrinsic_frequency + strato_coriolis_parameter
    pdf.add_page()
    pdf.set_font("Courier", size=12)
    # Print results
    if output_check == 0.0 or output_flag != 0:
        i = 0
        while i < 29:
            if i == 0:
                add_line_pdf(pdf, "Troposphere Data:", line_height, i*5)
            elif i == 15:
                center_add_line_pdf(pdf, f"The tropopause is at approximately {tropopause_altitude:.2f} km altitude.", line_height, (i*5)+5)
                add_line_pdf(pdf, "Stratosphere Data:", line_height, (i*5)+15)
            elif i >= 15:
                add_line_pdf(pdf, "No data within the extended altitude range/Not enough boundary coverage", line_height, (i*5)+15)
            else:
                add_line_pdf(pdf, "No data within the extended altitude range/Not enough boundary coverage", line_height, i*5)
            i = i + 1
    else:
        #debug code
        if debug:
            print("Troposphere Data:")
            print("Zbot (Km): ", tropo_z_bottom)
            print("Ztop (Km): ", tropo_z_top)
            print("horizontal wavelength (Km): ", tropo_horizontal_wavelength)
            print("vertical wavelength (Km): ", tropo_vertical_wavelength)
            print("mean phase propagation direction (deg): ", tropo_mean_phase_propagation_direction)
            print("upward propagation fraction: ", tropo_upward_propagation_fraction)
            print("zonal momentum flux (m^2/s^2): ", tropo_zonal_momentum_flux)
            print("meridional momentum flux (m^2/s^2): ", tropo_meridional_momentum_flux)
            print("potential energy (J/kg): ", tropo_potential_energy)
            print("Kinetic energy (J/kg): ", tropo_kinetic_energy)
            print("intrinsic frequency (s^-1): ", tropo_intrinsic_frequency)
            print("coriolis parameter: ", tropo_coriolis_parameter)
            print("\nStratosphere Data:\n")
            print("Zbot (Km): ", strato_z_bottom)
            print("Ztop (Km): ", strato_z_top)
            print("horizontal wavelength (Km): ", strato_horizontal_wavelength)
            print("vertical wavelength (Km): ", strato_vertical_wavelength)
            print("mean phase propagation direction (deg): ", strato_mean_phase_propagation_direction)
            print("upward propagation fraction: ", strato_upward_propagation_fraction)
            print("zonal momentum flux (m^2/s^2): ", strato_zonal_momentum_flux)
            print("meridional momentum flux (m^2/s^2): ", strato_meridional_momentum_flux)
            print("potential energy (J/kg): ", strato_potential_energy)
            print("Kinetic energy (J/kg): ", strato_kinetic_energy)
            print("intrinsic frequency (s^-1): ", strato_intrinsic_frequency)
            print("coriolis parameter: ", strato_coriolis_parameter)

        #printing to pdf
        add_line_pdf(pdf, "Troposphere Data:", line_height, 0)
        add_line_pdf(pdf, "Zbot (Km):                                                     " + str(tropo_z_bottom), line_height, 10)
        add_line_pdf(pdf, "Ztop (Km):                                                     " + str(tropo_z_top), line_height, 15)
        add_line_pdf(pdf, "Horizontal Wavelength (Km):                                    " + str(tropo_horizontal_wavelength), line_height, 20)
        add_line_pdf(pdf, "Vertical Wavelength (Km):                                      " + str(tropo_vertical_wavelength), line_height, 25)
        add_line_pdf(pdf, "Mean Phase Propagation Direction (deg):                        " + str(tropo_mean_phase_propagation_direction), line_height, 30)
        add_line_pdf(pdf, "Upward Propagation Fraction:                                   " + str(tropo_upward_propagation_fraction), line_height, 35)
        add_line_pdf(pdf, "Zonal Momentum Flux (m^2/s^2):                                 " + str(tropo_zonal_momentum_flux), line_height, 40)
        add_line_pdf(pdf, "Meridional Momentum Flux (m^2/s^2):                            " + str(tropo_meridional_momentum_flux), line_height, 45)
        add_line_pdf(pdf, "Potential Energy (J/Kg):                                       " + str(tropo_potential_energy), line_height, 50)
        add_line_pdf(pdf, "Kinetic Energy (J/Kg):                                         " + str(tropo_kinetic_energy), line_height, 55)
        add_line_pdf(pdf, "Intrinsic Frequency (s^-1):                                    " + str(tropo_intrinsic_frequency), line_height, 60)
        add_line_pdf(pdf, "Coriolis Parameter (J/Kg):                                     " + str(tropo_coriolis_parameter), line_height, 65)
        center_add_line_pdf(pdf, f"The tropopause is at approximately {tropopause_altitude:.2f} km altitude.", line_height, 75)
        add_line_pdf(pdf, "Stratosphere Data:", line_height, 85)
        add_line_pdf(pdf, "Zbot (Km):                                                     " + str(strato_z_bottom), line_height, 95)
        add_line_pdf(pdf, "Ztop (Km):                                                     " + str(strato_z_top), line_height, 100)
        add_line_pdf(pdf, "Horizontal Wavelength (Km):                                    " + str(strato_horizontal_wavelength), line_height, 105)
        add_line_pdf(pdf, "Vertical Wavelength (Km):                                      " + str(strato_vertical_wavelength), line_height, 110)
        add_line_pdf(pdf, "Mean Phase Propagation Direction (deg):                        " + str(strato_mean_phase_propagation_direction), line_height, 115)
        add_line_pdf(pdf, "Upward Propagation Fraction:                                   " + str(strato_upward_propagation_fraction), line_height, 120)
        add_line_pdf(pdf, "Zonal Momentum Flux (m^2/s^2):                                 " + str(strato_zonal_momentum_flux), line_height, 125)
        add_line_pdf(pdf, "Meridional Momentum Flux (m^2/s^2):                            " + str(strato_meridional_momentum_flux), line_height, 130)
        add_line_pdf(pdf, "Potential Energy (J/Kg):                                       " + str(strato_potential_energy), line_height, 135)
        add_line_pdf(pdf, "Kinetic Energy (J/Kg):                                         " + str(strato_kinetic_energy), line_height, 140)
        add_line_pdf(pdf, "Intrinsic Frequency (s^-1):                                    " + str(strato_intrinsic_frequency), line_height, 145)
        add_line_pdf(pdf, "Coriolis Parameter (J/Kg):                                     " + str(strato_coriolis_parameter), line_height, 150)
    # More debug code
    if debug:
        print("\nlines: ", line_number)
        print("output flag: ", output_flag)


def add_line_pdf(pdf, text, line_height, offset):
    """
    Adds single line to the pdf
    :param pdf: The pdf variable which the line will be added to
    :type pdf: PDF
    :param text: the line of text that you want to be added
    :type text: string
    :param line_height: Lines can be set to different height allowing you to change the spacing between lines
    :type line_height: float
    :param offset: Offset should be a persistent number you keep track of outside the function which specifies how far down the page the line should be printed
    :type line_height: float
    """

    # Sets offset
    pdf.set_y(offset)
    # Updates new offset for the next line
    offset += line_height
    # Adds the new line to the left side of the document
    pdf.cell(200, line_height, txt=text, ln=True, align='L')
    # Returns the updated value of offset so the next line will not be printed on top of this one
    return offset


def center_add_line_pdf(pdf, text, line_height, offset):
    """
    Adds line to the center of the pdf
    :param pdf: The pdf variable which the line will be added to
    :type pdf: PDF
    :param text: the line of text that you want to be added
    :type text: string
    :param line_height: Lines can be set to different height allowing you to change the spacing between lines
    :type line_height: float
    :param offset: Offset should be a persistent number you keep track of outside the function which specifies how far down the page the line should be printed
    :type line_height: float
    """

    # Sets offset
    pdf.set_y(offset)
    # Updates new offset for the next line
    offset += line_height
    # Adds the new line to the center of the document
    pdf.cell(200, line_height, txt=text, ln=True, align='C')
    # Returns the updated value of offset so the next line will not be printed on top of this one
    return offset


def add_graph_to_pdf(pdf, graph_path, x_cord, y_cord, width, height):
    """
    Adds single graph to the pdf
    :param pdf: The pdf variable which the line will be added to
    :type pdf: PDF
    :param graph_path: The path to the graph .png photo
    :type graph_path: String
    :param x_cord: The x_coordinate where the graph should be printed
    :type x_cord: float
    :param y_cord: The x_coordinate where the graph should be printed
    :type y_cord: float
    :param height: How tall the graph should be
    :type height: float
    :param width: How wide the graph should be
    :type width: float
    """

    # Adds image to pdf
    pdf.image(graph_path, x=x_cord, y=y_cord, w=width, h=height, compress=False)
    # Updates new Y_coordinate
    y_cord += height  # Corrected to add height instead of width - 10
    # returns new Y_coordinate
    return y_cord


def one_graph_per_page(pdf, image_width, image_height, temp_directory, tropopause_altitude, line_height):
    """
    Prints all the graphs in a certain directory, one per page
    :param pdf: The pdf variable which the line will be added to
    :type pdf: PDF
    :param image_width: The desired width of the image
    :type image_width: float
    :param image_height: The desired height of the image
    :type image_height: float
    :param temp_directory: The temporary directory where the .png images are being stored
    :type temp_directory: String
    :param tropopause_altitude: the altitude of the tropopause
    :type tropopause_altitude: float
    :param line_height: The desired line height
    :type line_height: float
    """

    # Gathers the .png image files from the desired directory
    files = os.listdir(temp_directory)
    # Sort files based on creation time
    sorted_files = sorted(files, key=lambda f: os.path.getctime(os.path.join(temp_directory, f)))
    # Loop through the directory
    for filename in sorted_files:
        filepath = os.path.join(temp_directory, filename)
        # Check if it is a file
        if os.path.isfile(filepath) and filename.endswith(".png"):
            page_width = pdf.w
            # page_height = pdf.h
            x_coordinate = (page_width - image_width) / 2
            y_coordinate = 50
            pdf.add_page()
            pdf.image(filepath, x=x_coordinate, y=y_coordinate, w=image_width, h=image_height)
    pdf.set_font("Arial", size=12)
    y_coordinate += 200
    add_line_pdf(pdf, f"The tropopause is at approximately {tropopause_altitude:.2f} km altitude.", line_height, y_coordinate)


def create_pdf(pdf, line_height, image_width, image_height, string_list, init_file_name, file_format, data, header_units, lat_long_coordinates, temp_directory):
    """
    Main script used for creating the pdf
    :param pdf: The pdf variable which the line will be added to
    :type pdf: PDF
    :param image_width: The desired width of the image
    :type image_width: float
    :param image_height: The desired height of the image
    :type image_height: float
    :param temp_directory: The temporary directory where the .png images are being stored
    :type temp_directory: String
    :param line_height: The desired line height
    :type line_height: float
    :param string_list: A list of the strings that you want added to the pdf
    :type string_list: List of Strings
    :param init_file_name: The name you want the pdf file to be called
    :type init_file_name: String
    :param file_format: The desired file format
    :type file_format: String
    :param data: The array of weather data
    :type data: array
    :param header_units: The units that correspond with each column of data
    :type header_units: array
    :param lat_long_coordinates: The lat and long coordinates of the balloon
    :type lat_long_coordinates: array
    """

    # Get the requested directory to put all files in:
    pdf_directory = "/src/data"

    pdf.add_page()
    pdf.set_font("Times", size=12)
    current_offset = pdf.h / 2.5  # Initial offset for text
    for line in string_list:
        current_offset = center_add_line_pdf(pdf, line, line_height, current_offset)

    # Get all graphs for PDF
    display_us_lat_long_map(lat_long_coordinates, file_format, temp_directory)

    # Find the tropopause using gradient method
    tropopause_altitude = find_tropopause_with_gradient(data)
    print(f"The tropopause is at approximately {tropopause_altitude:.2f} km altitude.")

    # Find the tropopause and adjust it for the plots
    end_at_tropopause = find_tropopause_with_gradient(data) - 1.5  # 1.5 km noise removed
    begin_at_tropopause = find_tropopause_with_gradient(data) + 1.5  # 1.5 km noise removed

    # Troposphere mean temperature
    plot_temperature_vs_altitude_troposphere(data, header_units, end_at_tropopause, temp_directory)

    # Troposphere prime temperature
    plot_temperature_perturbation_troposphere(data, end_at_tropopause, temp_directory)

    # Stratosphere mean temperature
    plot_temperature_vs_altitude_stratosphere(data, header_units, begin_at_tropopause, temp_directory)

    # Stratosphere prime temperature
    plot_temperature_perturbation_stratosphere(data, begin_at_tropopause, temp_directory)

    # Hodographs
    plot_hodograph_with_color_troposphere(data, end_at_tropopause, temp_directory)
    plot_hodograph_with_color_stratosphere(data, begin_at_tropopause, temp_directory)

    # Ascension Rates
    plot_ascension_rate_troposphere(data, end_at_tropopause, temp_directory)
    plot_ascension_rate_stratosphere(data, begin_at_tropopause, temp_directory)

    # Troposphere u and v
    plot_wind_u_troposphere(data, end_at_tropopause, temp_directory)
    plot_wind_v_troposphere(data, end_at_tropopause, temp_directory)

    # Stratosphere u and v
    plot_wind_u_stratosphere(data, begin_at_tropopause, temp_directory)
    plot_wind_v_stratosphere(data, begin_at_tropopause, temp_directory)

    # Troposphere u' and v' perturbations
    plot_u_perturbation_troposphere(data, end_at_tropopause, temp_directory)
    plot_v_perturbation_troposphere(data, end_at_tropopause, temp_directory)

    # # Stratosphere u' and v' perturbations
    plot_u_perturbation_stratosphere(data, begin_at_tropopause, temp_directory)
    plot_v_perturbation_stratosphere(data, begin_at_tropopause, temp_directory)

    one_graph_per_page(pdf, image_width, image_height, temp_directory, tropopause_altitude, line_height)

    # Put the GDL data into the PDF file
    main_gdl(pdf, line_height, tropopause_altitude)

    # Save the PDF in a user-requested directory
    output_directory = os.path.join(os.getcwd(), pdf_directory)

    # Check if the file already exists, if it does, generate a new filename
    file_path = os.path.join(output_directory, init_file_name + ".pdf")
    duplicate_cnt = 0
    while os.path.exists(file_path):
        duplicate_cnt += 1
        file_name = f"{init_file_name}({duplicate_cnt})"
        file_path = os.path.join(output_directory, file_name + ".pdf")
    pdf.output(file_path)
    if duplicate_cnt == 0:
        print("Radiosonde PDF has been saved to \"" + output_directory + "\" directory as " + init_file_name + ".pdf\n")
    else:
        print("Radiosonde PDF has been saved to \"" + output_directory + "\" directory as " + file_name + ".pdf\n")
