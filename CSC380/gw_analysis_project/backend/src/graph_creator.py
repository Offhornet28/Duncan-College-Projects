import os
import matplotlib.pyplot as plt
import numpy as np
from metpy.plots import Hodograph
from scipy.interpolate import interp1d
from matplotlib import colormaps
from datetime import datetime
import cartopy.feature
import cartopy.crs as ccrs
import uuid
from matplotlib.ticker import FuncFormatter, FixedLocator

list(colormaps)


def is_decreasing(altitudes):
    # Check if there's a decreasing trend for at least 5 consecutive points
    decreasing_count = 0
    for i in range(1, len(altitudes)):
        if altitudes[i] < altitudes[i - 1]:
            decreasing_count += 1
            if decreasing_count >= 5:
                return True
        else:
            decreasing_count = 0  # Reset count if not decreasing
    return False


def add_cities(ax, lat_long_data):
    """
    Add the latitude & longitude coordinates of major cities to the given map
    :param ax: The United States map image
    :param lat_long_data: The list of latitude and longitude coordinates
    :type lat_long_data: list(tuple)
    """

    # Major US city coordinates provided by https://gps-coordinates.org/
    cities = {
        'Burlington': (44.475933, -73.212831),
        'Buffalo': (42.886527, -78.878776),
        'Boston': (42.3602558, -71.0572791),
        'Bangor': (44.8033898, -68.7706018),
        'New York': (40.7129822, -74.007205),
        'Pittsburgh': (40.4378596, -79.996257),
        'Charleston': (38.3510215, -81.6381493),
        'Washington, DC': (38.8954381, -77.0312812),
        'Nashville': (36.1660667, -86.777802),
        'Charlotte': (35.2216943, -80.839734),
        'Jackson': (32.297876, -90.183221),
        'Atlanta': (33.748188, -84.390865),
        'Orlando': (28.53798, -81.3787378),
        'Tampa': (27.947896, -82.457335),
        'Miami': (25.7708431, -80.1976364),
        'New Orleans': (29.952535, -90.076688),
        'Detroit': (42.32939, -83.044933),
        'Cincinnati': (39.104064, -84.520346),
        'Chicago': (41.883718, -87.632382),
        'St. Louis': (38.627631, -90.199087),
        'Kansas City': (39.113036, -94.626809),
        'Minneapolis': (44.977766, -93.265067),
        'Bismarck': (46.8071131, -100.7846367),
        'Denver': (39.739212, -104.9903028),
        'Dallas': (32.7755679, -96.7955954),
        'Houston': (29.7603761, -95.3698054),
        'Seattle': (47.6037757, -122.3307651),
        'Billings': (45.783868, -108.505713),
        'Boise': (43.615346, -116.201974),
        'Portland': (45.515118, -122.679485),
        'Salt Lake City': (40.759551, -111.888196),
        'San Francisco': (37.779379, -122.418433),
        'Las Vegas': (36.1667469, -115.1487083),
        'Los Angeles': (34.053345, -118.242349),
        'Phoenix': (33.448253, -112.076617),
        'Albuquerque': (35.0882444, -106.6515469)
    }
    for city, (lat, lon) in cities.items():
        ax.plot(lon, lat, 'o', markersize=3, color='red', transform=ccrs.PlateCarree())
        text = ax.text(lon + 0.25, lat + 0.25, city, transform=ccrs.PlateCarree(), fontsize=6, color='blue',
                       weight='bold')
        text.set_bbox(dict(facecolor='white', alpha=0.5, edgecolor='none', pad=0.5))


def display_us_lat_long_map(lat_long_data, x, temp_directory):
    """
    Display the US latitude and longitude map with a list of given (latitude, longitude) coordinates.
    :param lat_long_data: The list of latitude and longitude coordinates
    :param x: The file type
    :param temp_directory: The directory that will temporarily store the graph files

    :type lat_long_data: list(tuple)
    """
    largest_lat = max(lat_long_data, key=lambda y: y[0])
    smallest_lat = min(lat_long_data, key=lambda y: y[0])
    largest_long = max(lat_long_data, key=lambda y: y[1])
    smallest_long = min(lat_long_data, key=lambda y: y[1])

    if x == "read_original_data":
        largest_lat = largest_lat[0]
        smallest_lat = smallest_lat[0]
        largest_long = largest_long[1]
        smallest_long = smallest_long[1]
    else:
        largest_lat = largest_lat[1]
        smallest_lat = smallest_lat[1]
        largest_long = largest_long[0]
        smallest_long = smallest_long[0]

    # Create a plot with a specific projection (PlateCarree for latitude/longitude)
    fig = plt.figure(figsize=(10, 10))
    ax = fig.add_subplot(1, 1, 1, projection=ccrs.PlateCarree())

    # Draw latitude and longitude lines
    ax.gridlines(draw_labels=True)

    # Set extent to the United States
    if x == "read_original_data" or x == "read_2023_data":
        ax.set_extent([smallest_long - 5, largest_long + 5, smallest_lat - 5, largest_lat + 5], crs=ccrs.PlateCarree())
    else:
        ax.set_extent([smallest_lat - 5, largest_lat + 5, smallest_long - 5, largest_long + 5], crs=ccrs.PlateCarree())

    # Add some context (coastlines, land, ocean, rivers)
    ax.coastlines()
    cartopy.config["data_dir"] = '/src/cartopy'
    ax.add_feature(cartopy.feature.LAND)
    ax.add_feature(cartopy.feature.OCEAN)
    ax.add_feature(cartopy.feature.RIVERS)
    ax.add_feature(cartopy.feature.LAKES)
    ax.add_feature(cartopy.feature.BORDERS)
    ax.add_feature(cartopy.feature.STATES)

    # add_cities(ax, lat_long_data)
    if x == "read_2023_data":
        for (lat, lon) in lat_long_data:
            ax.plot(lat, lon, 'o', markersize=1, color='purple', transform=ccrs.PlateCarree())
    else:
        for (lat, lon) in lat_long_data:
            ax.plot(lon, lat, 'o', markersize=1, color='purple', transform=ccrs.PlateCarree())

    plt.title('Weather Balloon Path', y=1.05)

    # Add description below the plot
    description = "Figure Description: This graph displays the weather balloon path \n " \
                  "on a geopolitical map using it's recorded latitude and longitude."
    plt.figtext(0.5, 0.03, description, wrap=True, horizontalalignment='center', fontsize=10)

    save_to_directory(plt, temp_directory)

    plt.show()


def read_original_data(file_path):
    """
    Reads and processes original data into Lists
    :param file_path: The file path to the original data .txt file
    :type lat_long_data: String
    """

    # Create Variable names for each column of data
    variables = [
        "Time", "P", "T", "Hu", "Ws", "Wd", "Long.", "Lat.",
        "Alt", "Geopot", "MRI", "RI", "Dewp.", "Virt. Temp", "Rs",
        "Elevation", "Azimuth", "Range", "D"
    ]

    # Create unit names for each column of data
    units = [
        "sec", "hPa", "°C", "%", "m/s", "°", "°", "°",
        "km", "m", "", "", "m/s",
        "°", "°", "m", "kg/m3"
    ]

    # Creates dictionary where each key is a variable name and the value is an empty list
    data = {variable: [] for variable in variables}

    # Creates dictionary where each key is a variable name and each value is a unity
    header_units = dict(zip(variables, units))

    # Opens File and reads lines
    with open(file_path, 'r', encoding='ISO-8859-1') as file:
        lines = file.readlines()

    # Iterates though each line until the word "profile data" is found
    start_index = 0
    for i, line in enumerate(lines):
        if "Profile Data:" in line:
            start_index = i + 3
            break

    # List of invalid_values
    invalid_values = {"999999", "1000002", "-", "--", "999999.0", "999999.00", "999999.000", "-9999.9"}

    # Keep track of altitudes to check the decreasing trend
    temp_altitudes = []

    for line in lines[start_index:]:
        if not line.strip():
            continue  # Skip empty lines

        # Gets the value of the line without any trailing whitespaces
        values = line.strip().split()

        # Skips any invalid values or periods
        if any(val in invalid_values or val.strip('.') in invalid_values for val in values):
            continue
        if len(values) < len(variables):
            continue

        # List to store processed values
        processed_values = []

        # Iterates through values
        for i, value in enumerate(values):
            try:
                var = variables[i]
                if var == "Alt":
                    altitude = float(value) * 0.001  # Convert Alt from meters to kilometers
                    processed_values.append(altitude)
                    temp_altitudes.append(altitude)  # Corrected to append altitude only
                else:
                    processed_values.append(float(value))
            except ValueError:
                print(f"Error converting value to float: {value}")
                break  # Break out of the loop to skip this line

        if is_decreasing(temp_altitudes):
            break  # Exit the loop if decreasing trend is found

        if len(processed_values) == len(variables):
            for i, var in enumerate(variables):
                data[var].append(processed_values[i])

    # Returns data and header_units dictionary's
    return data, header_units


def save_to_directory(plt, directory):
    """
    Saves plotted graph to a given directory as a .png file
    :param plt: The graph you want to save
    :type plt: Graph
    :param directory: The directory you want the graph saved to
    :type directory: String
    """

    # Generate a unique ID for each graph to avoid overriding
    file_id = str(uuid.uuid4())

    #Saves plotted graph to a given directory as a .png file
    plt.savefig(os.path.join(directory, file_id + ".png"))


def read_2023_data(file_path):
    variables = [
        "UTC", "Lat.", "Long.", "P", "T", "Hu", "Ws", "Wd", "Alt", "D", "Rs"
    ]

    units = [
        "HH:mm:ss", "°", "°", "hPa", "°C", "%", "m/s", "°",
        "km", "kg/m3", "m/s"
    ]

    data = {variable: [] for variable in variables}
    header_units = dict(zip(variables, units))

    with open(file_path, 'r', encoding='ISO-8859-1') as file:
        lines = file.readlines()

    start_index = 0
    for i, line in enumerate(lines):
        start_index = i + 4
        break

    invalid_values = {"999999", "1000002", "-", "--", "999999.0", "-999.9", "999999.00", "-9999.9"}
    essential_vars = {"UTC", "Long", "Lat", "P", "T", "Ws", "Wd", "Alt"}

    temp_altitudes = []  # Keep track of altitudes to check the decreasing trend
    for line in lines[start_index:]:
        if not line.strip():
            continue  # Skip empty lines

        values = line.strip().split()

        if len(values) < len(essential_vars):  # Ensure enough values are present
            continue

        # Check only essential variables for invalid values
        if any(values[i] in invalid_values or values[i].strip('.') in invalid_values for i, var in enumerate(variables)
               if var in essential_vars):
            continue  # Skip this line if any essential value is invalid

        processed_values = [None] * len(variables)
        for i, value in enumerate(values):
            if i >= len(variables):
                break

            if value in invalid_values or value.strip('.') in invalid_values:
                processed_values[i] = None
                continue

            var = variables[i]
            if var == "UTC":
                processed_values[i] = datetime.strptime(value, "%H:%M:%S").time()
            elif var == "Alt":
                altitude = float(value) * 0.001  # Convert from meters to kilometers
                processed_values[i] = altitude
                temp_altitudes.append(altitude)  # Add to temp_altitudes for trend checking
            else:
                processed_values[i] = float(value)
            """
            except ValueError as e:
                print(f"Error processing value {value} for variable {var}: {e}")
                processed_values[i] = None
            """

        # Check for decreasing trend outside the values processing loop
        if is_decreasing(temp_altitudes):
            break  # Exit the loop if decreasing trend is found

        # Add processed values to data only if essential variables are valid
        if all(processed_values[i] is not None for i, var in enumerate(variables) if var in essential_vars):
            for i, var in enumerate(variables):
                if processed_values[i] is not None:  # Add only if value was successfully processed
                    data[var].append(processed_values[i])

    return data, header_units


def read_dr_barber_data(file_path):
    variables = [
        "Time", "UTC", "P", "T", "Hu", "Ws", "Wd", "Long.", "Lat.",
        "Alt", "Geopot", "RI", "Dewp.", "Elevation", "Azimuth", "Range", "D"
    ]

    units = [
        "sec", "HH:mm:ss", "hPa", "°C", "%", "m/s", "°", "°", "°",
        "km", "m", "", "°C", "°", "°", "m", "kg/m3"
    ]

    data = {variable: [] for variable in variables}
    header_units = dict(zip(variables, units))

    with open(file_path, 'r', encoding='ISO-8859-1') as file:
        lines = file.readlines()

    start_index = 0
    for i, line in enumerate(lines):
        if "Profile Data:" in line:
            start_index = i + 5
            break

    invalid_values = {"999999", "1000002", "-", "--", "999999.0", "999999.00", "-999.9", "-9999.9"}
    essential_vars = {"Time", "UTC", "P", "T", "Ws", "Wd", "Long.", "Lat.", "Alt"}

    temp_altitudes = []  # Keep track of altitudes to check the decreasing trend
    for line in lines[start_index:]:
        if not line.strip():
            continue  # Skip empty lines

        values = line.strip().split()

        # Ensure enough values are present for all variables
        if len(values) < len(variables):
            continue

        # Check only essential variables for invalid values
        essential_indices = [variables.index(var) for var in essential_vars if var in variables]
        if any(values[i] in invalid_values or values[i].strip('.') in invalid_values for i in essential_indices):
            continue  # Skip this line if any essential value is invalid

        processed_values = [None] * len(variables)
        for i, value in enumerate(values):
            if i >= len(variables):
                break

            var = variables[i]
            if value in invalid_values or value.strip('.') in invalid_values:
                processed_values[i] = None
                continue

            if var == "UTC":
                processed_values[i] = datetime.strptime(value, "%H:%M:%S").time()
            elif var == "Alt":
                altitude = float(value) * 0.001  # Convert from meters to kilometers
                processed_values[i] = altitude
                temp_altitudes.append(altitude)  # Add to temp_altitudes for trend checking
            else:
                processed_values[i] = float(value)
            """
            except ValueError as e:
                print(f"Error processing value {value} for variable {var}: {e}")
                processed_values[i] = None
            """

        # Check for decreasing trend outside the values processing loop
        if is_decreasing(temp_altitudes):
            break  # Exit the loop if decreasing trend is found

        # Add processed values to data only if essential variables are valid
        if all(processed_values[i] is not None for i in essential_indices):
            for i, var in enumerate(variables):
                if processed_values[i] is not None:  # Add only if value was successfully processed
                    data[var].append(processed_values[i])

    return data, header_units


def read_dr_barber_data_v2(file_path):
    variables = [
        "Time", "UTC", "P", "T", "Hu", "Ws", "Wd", "Long.", "Lat.",
        "Alt", "Geopot", "RI", "Dewp.", "Rs", "Elevation", "Azimuth", "Range", "D"
    ]

    units = [
        "sec", "HH:mm:ss", "hPa", "°C", "%", "m/s", "°", "°", "°",
        "km", "m", "", "°C", "m/s", "°", "°", "m", "kg/m3"
    ]

    data = {variable: [] for variable in variables}
    header_units = dict(zip(variables, units))

    with open(file_path, 'r', encoding='ISO-8859-1') as file:
        lines = file.readlines()

    start_index = 0
    for i, line in enumerate(lines):
        if "Profile Data:" in line:
            start_index = i + 5
            break

    invalid_values = {"999999", "1000002", "-", "--", "999999.0", "999999.00", "-999.9", "-9999.9"}
    essential_vars = {"Time", "UTC", "P", "T", "Ws", "Wd", "Long.", "Lat.", "Alt"}

    temp_altitudes = []  # Keep track of altitudes to check the decreasing trend
    for line in lines[start_index:]:
        if not line.strip():
            continue  # Skip empty lines

        values = line.strip().split()

        # Ensure enough values are present for all variables
        if len(values) < len(variables):
            continue

        # Check only essential variables for invalid values
        essential_indices = [variables.index(var) for var in essential_vars if var in variables]
        if any(values[i] in invalid_values or values[i].strip('.') in invalid_values for i in essential_indices):
            continue  # Skip this line if any essential value is invalid

        processed_values = [None] * len(variables)
        for i, value in enumerate(values):
            if i >= len(variables):
                break

            var = variables[i]
            if value in invalid_values or value.strip('.') in invalid_values:
                processed_values[i] = None
                continue

            if var == "UTC":
                processed_values[i] = datetime.strptime(value, "%H:%M:%S").time()
            elif var == "Alt":
                altitude = float(value) * 0.001  # Convert from meters to kilometers
                processed_values[i] = altitude
                temp_altitudes.append(altitude)  # Add to temp_altitudes for trend checking
            else:
                processed_values[i] = float(value)
            """
            except ValueError as e:
                print(f"Error processing value {value} for variable {var}: {e}")
                processed_values[i] = None
            """

        # Check for decreasing trend outside the values processing loop
        if is_decreasing(temp_altitudes):
            break  # Exit the loop if decreasing trend is found

        # Add processed values to data only if essential variables are valid
        if all(processed_values[i] is not None for i in essential_indices):
            for i, var in enumerate(variables):
                if processed_values[i] is not None:  # Add only if value was successfully processed
                    data[var].append(processed_values[i])

    return data, header_units


def plot_data(data, header_units, temp_directory):
    print("Available variables:", ', '.join(data.keys()))
    x_var = input("Enter the X-axis variable: ")
    y_var = input("Enter the Y-axis variable: ")

    if x_var not in data or y_var not in data:
        print("Invalid variable names.")
        return

    x_data = np.array(data[x_var])
    y_data = np.array(data[y_var])

    plt.figure(figsize=(10, 6))

    # Preparing custom ticks for pressures divisible by 200 down to 100 hPa
    pressure_ticks = np.arange(100, 1100, 200)
    log_pressure_ticks = np.log(pressure_ticks)  # Logarithmic positions for plotting

    # Function to format the tick labels
    def exp_format(x, pos):
        # Convert log scale tick back to hPa value for label
        return f'{np.exp(x):.0f}hPa'

    if x_var == 'P':
        x_data = np.log(x_data)
        plt.gca().xaxis.set_major_locator(FixedLocator(log_pressure_ticks))
        plt.gca().xaxis.set_major_formatter(FuncFormatter(exp_format))
        x_label = f"log({x_var}) [{header_units[x_var]}]"
    else:
        x_label = f"{x_var} [{header_units[x_var]}]"

    if y_var == 'P':
        y_data = np.log(y_data)
        plt.gca().yaxis.set_major_locator(FixedLocator(log_pressure_ticks))
        plt.gca().yaxis.set_major_formatter(FuncFormatter(exp_format))
        y_label = f"log({y_var}) [{header_units[y_var]}]"
    else:
        y_label = f"{y_var} [{header_units[y_var]}]"

    plt.plot(x_data, y_data, marker='', color='k', linestyle='solid', label='Original Data')

    plt.xlabel(x_label)
    plt.ylabel(y_label)
    plt.title(f"{y_var} vs {x_var}")
    plt.legend()
    plt.grid(True)

    if y_var == 'P':
        plt.gca().invert_yaxis()  # Inverting Y-axis if it's pressure

    save_to_directory(plt, temp_directory)
    plt.show()


def calculate_wind_components(speeds, directions):
    speeds = np.array(speeds)
    directions = np.array(directions)

    u = -speeds * np.sin(np.radians(directions))
    v = -speeds * np.cos(np.radians(directions))
    return u, v


def interpolate_temperature_data(temperatures, altitudes, step=0.1):
    # Find minimum and maximum altitudes to define grid
    alt_min = np.min(altitudes)
    alt_max = np.max(altitudes)
    # Create a uniform grid of altitudes with the specified step
    uniform_altitudes = np.arange(alt_min, alt_max, step)

    # Linear interpolation of temperatures at the uniform altitudes
    interpolate_func = interp1d(altitudes, temperatures, kind='linear')
    interpolated_temperatures = interpolate_func(uniform_altitudes)

    return interpolated_temperatures, uniform_altitudes


def plot_hodograph_with_color_troposphere(data, max_altitude, temp_directory):
    speeds = np.array(data['Ws'])
    directions = np.array(data['Wd'])
    altitudes = np.array(data['Alt'])
    u = -speeds * np.sin(np.radians(directions))
    v = -speeds * np.cos(np.radians(directions))

    # Apply mask for data below the specified max_altitude
    mask = altitudes <= max_altitude
    filtered_u = u[mask]
    filtered_v = v[mask]
    filtered_altitudes = altitudes[mask]

    # Fit a polynomial to act as the mean temperature curve
    coeffs_u = np.polyfit(filtered_altitudes, filtered_u, deg=3)
    mean_u_curve = np.polyval(coeffs_u, filtered_altitudes)
    coeffs_v = np.polyfit(filtered_altitudes, filtered_v, deg=3)
    mean_v_curve = np.polyval(coeffs_v, filtered_altitudes)

    # Calculate temperature perturbations by subtracting the mean temperature curve values
    u_perturbations = filtered_u - mean_u_curve
    v_perturbations = filtered_v - mean_v_curve

    # Define color boundaries based on the filtered altitudes
    alt_max = filtered_altitudes.max()
    boundaries = np.arange(0, alt_max, 1)  # Create boundaries every 1 km

    # Create normalized colors list from the colormap
    cmap = plt.cm.hsv
    norm = plt.Normalize(vmin=0, vmax=alt_max)
    colors = [cmap(norm(value)) for value in boundaries]

    # Create figure and hodograph object
    fig, ax = plt.subplots(1, 1, figsize=(11.5, 10))
    from mpl_toolkits.axes_grid1.inset_locator import inset_axes
    plt.title('Hodograph (Troposphere)', loc='left')
    plt.xlabel("u' (m/s)")
    plt.ylabel("v' (m/s)")
    axins = inset_axes(ax,
                       width="5%",  # width = 5% of parent_bbox width
                       height="100%",
                       loc='lower left',
                       bbox_to_anchor=(1.05, 0., 1, 1),
                       bbox_transform=ax.transAxes,
                       borderpad=0,
                       )
    h = Hodograph(ax, component_range=9)
    #h.add_grid(increment=3)
    l = h.plot_colormapped(u_perturbations, v_perturbations, filtered_altitudes, intervals=boundaries, colors=colors)

    # Add colorbar
    cb = plt.colorbar(l, cax=axins, orientation="vertical")
    cb.set_label('Altitude (km)')

    # Add description below the plot
    description = "Figure Description: This graph displays the u and v wind component \n " \
                  "perturbations in the troposphere. The altitude is denoted by the colorbar."
    plt.figtext(0.5, 0.03, description, wrap=True, horizontalalignment='center', fontsize=10)

    save_to_directory(plt, temp_directory)

    plt.show()


def plot_hodograph_with_color_stratosphere(data, min_altitude, temp_directory):
    speeds = np.array(data['Ws'])
    directions = np.array(data['Wd'])
    altitudes = np.array(data['Alt'])
    u = -speeds * np.sin(np.radians(directions))
    v = -speeds * np.cos(np.radians(directions))

    # Apply mask for data below the specified max_altitude
    mask = altitudes >= min_altitude
    filtered_u = u[mask]
    filtered_v = v[mask]
    filtered_altitudes = altitudes[mask]

    # Fit a polynomial to act as the mean temperature curve
    coeffs_u = np.polyfit(filtered_altitudes, filtered_u, deg=3)
    mean_u_curve = np.polyval(coeffs_u, filtered_altitudes)
    coeffs_v = np.polyfit(filtered_altitudes, filtered_v, deg=3)
    mean_v_curve = np.polyval(coeffs_v, filtered_altitudes)

    # Calculate temperature perturbations by subtracting the mean temperature curve values
    u_perturbations = filtered_u - mean_u_curve
    v_perturbations = filtered_v - mean_v_curve

    # Define color boundaries based on the filtered altitudes
    alt_min = filtered_altitudes.min()
    alt_max = filtered_altitudes.max()
    boundaries = np.arange(alt_min, alt_max, 1)  # Create boundaries every 1 km

    # Create normalized colors list from the colormap
    cmap = plt.cm.hsv
    norm = plt.Normalize(vmin=alt_min, vmax=alt_max)
    colors = [cmap(norm(value)) for value in boundaries]

    # Create figure and hodograph object
    fig, ax = plt.subplots(1, 1, figsize=(11.5, 10))
    from mpl_toolkits.axes_grid1.inset_locator import inset_axes
    plt.title('Hodograph (Stratosphere)', loc='left')
    plt.xlabel("u' (m/s)")
    plt.ylabel("v' (m/s)")
    axins = inset_axes(ax,
                       width="5%",  # width = 5% of parent_bbox width
                       height="100%",
                       loc='lower left',
                       bbox_to_anchor=(1.05, 0., 1, 1),
                       bbox_transform=ax.transAxes,
                       borderpad=0,
                       )
    h = Hodograph(ax, component_range=9)
    #h.add_grid(increment=3)
    # Plot hodograph with colors based on altitude
    l = h.plot_colormapped(u_perturbations, v_perturbations, filtered_altitudes, intervals=boundaries, colors=colors)

    # Add colorbar
    cb = plt.colorbar(l, cax=axins, orientation="vertical")
    cb.set_label('Altitude (km)')

    # Add description below the plot
    description = "Figure Description: This graph displays the u and v wind component \n " \
                  "perturbations in the stratosphere. The altitude is denoted by the colorbar."
    plt.figtext(0.5, 0.03, description, wrap=True, horizontalalignment='center', fontsize=10)

    save_to_directory(plt, temp_directory)

    plt.show()


def plot_temperature_vs_altitude_troposphere(data, header_units, max_altitude, temp_directory):
    plt.figure(figsize=(10, 10))
    filtered_indices = [i for i, alt in enumerate(data['Alt']) if alt <= max_altitude]
    filtered_temps = [data['T'][i] for i in filtered_indices]
    filtered_alts = [data['Alt'][i] for i in filtered_indices]

    plt.plot(filtered_temps, filtered_alts, marker='', color='k', linestyle='solid', label='Temperature Data')
    coeffs = np.polyfit(filtered_alts, filtered_temps, 3)
    y_curve = np.linspace(min(filtered_alts), max(filtered_alts), 500)
    x_curve = np.polyval(coeffs, y_curve)

    plt.plot(x_curve, y_curve, 'r-', label='Least Squares Regression Best Fit')
    plt.xlabel(f"Temperature [{header_units['T']}]")
    plt.ylabel(f"Altitude [{header_units['Alt']}]")
    plt.title("Temperature vs. Altitude (Troposphere)")
    plt.legend()
    plt.grid(True)

    # Add description below the plot
    description = "Figure Description: This graph displays temperature with respect to altitude in \n " \
                  "the troposphere. The polynomial regression line of temperature is plotted in red."
    plt.figtext(0.5, 0.03, description, wrap=True, horizontalalignment='center', fontsize=10)

    save_to_directory(plt, temp_directory)

    plt.show()


def plot_temperature_vs_altitude_stratosphere(data, header_units, min_altitude, temp_directory):
    plt.figure(figsize=(10, 10))
    filtered_indices = [i for i, alt in enumerate(data['Alt']) if alt >= min_altitude]
    filtered_temps = [data['T'][i] for i in filtered_indices]
    filtered_alts = [data['Alt'][i] for i in filtered_indices]

    plt.plot(filtered_temps, filtered_alts, marker='', color='k', linestyle='solid', label='Temperature Data')
    coeffs = np.polyfit(filtered_alts, filtered_temps, 2)
    y_curve = np.linspace(min(filtered_alts), max(filtered_alts), 500)
    x_curve = np.polyval(coeffs, y_curve)

    plt.plot(x_curve, y_curve, 'r-', label='Least Squares Regression Best Fit')
    plt.xlabel(f"Temperature [{header_units['T']}]")
    plt.ylabel(f"Altitude [{header_units['Alt']}]")
    plt.title("Temperature vs. Altitude (Stratosphere)")
    plt.legend()
    plt.grid(True)

    # Add description below the plot
    description = "Figure Description: This graph displays temperature with respect to altitude in \n " \
                  "the stratosphere. The polynomial regression line of temperature is plotted in red."
    plt.figtext(0.5, 0.03, description, wrap=True, horizontalalignment='center', fontsize=10)

    save_to_directory(plt, temp_directory)

    plt.show()


def plot_temperature_perturbation_troposphere(data, max_altitude, temp_directory):
    plt.figure(figsize=(10, 10))
    temperatures = np.array(data['T'])
    altitudes = np.array(data['Alt'])

    # Apply mask for data below the specified max_altitude
    mask = altitudes <= max_altitude
    filtered_temperatures = temperatures[mask]
    filtered_altitudes = altitudes[mask]

    # Fit a polynomial to act as the mean temperature curve
    coeffs = np.polyfit(filtered_altitudes, filtered_temperatures, deg=3)
    mean_temperature_curve = np.polyval(coeffs, filtered_altitudes)

    # Calculate temperature perturbations by subtracting the mean temperature curve values
    temperature_perturbations = filtered_temperatures - mean_temperature_curve

    # Plotting the raw temperature perturbation values
    plt.plot(temperature_perturbations, filtered_altitudes, color='k',
             label="Temperature Perturbation (T')",
             marker='',
             linestyle='solid')

    plt.xlabel("Temperature Perturbation (T') [°C]")
    plt.ylabel("Altitude [km]")
    plt.title("Temperature Perturbation (T') (Troposphere)")
    plt.legend()
    plt.grid(True)

    # Add description below the plot
    description = "Figure Description: This graph displays the temperature perturbations from \n" \
                  "the mean temperature (T mean + T’) with respect to altitude in the troposphere. "
    plt.figtext(0.5, 0.03, description, wrap=True, horizontalalignment='center', fontsize=10)

    save_to_directory(plt, temp_directory)

    plt.show()


def plot_temperature_perturbation_stratosphere(data, min_altitude, temp_directory):
    plt.figure(figsize=(10, 10))
    temperatures = np.array(data['T'])
    altitudes = np.array(data['Alt'])

    # Apply mask for data below the specified max_altitude
    mask = altitudes >= min_altitude
    filtered_temperatures = temperatures[mask]
    filtered_altitudes = altitudes[mask]

    # Fit a polynomial to act as the mean temperature curve
    coeffs = np.polyfit(filtered_altitudes, filtered_temperatures, deg=3)
    mean_temperature_curve = np.polyval(coeffs, filtered_altitudes)

    # Calculate temperature perturbations by subtracting the mean temperature curve values
    temperature_perturbations = filtered_temperatures - mean_temperature_curve

    # Plotting the raw temperature perturbation values
    plt.plot(temperature_perturbations, filtered_altitudes, color='k',
             label="Temperature Perturbation (T')",
             marker='',
             linestyle='solid')

    plt.xlabel("Temperature Perturbation (T') [°C]")
    plt.ylabel("Altitude [km]")
    plt.title("Temperature Perturbation (T') (Stratosphere)")
    plt.legend()
    plt.grid(True)

    # Add description below the plot
    description = "Figure Description: This graph displays the temperature perturbations from \n" \
                  "the mean temperature (T mean + T’) with respect to altitude in the stratosphere. "
    plt.figtext(0.5, 0.03, description, wrap=True, horizontalalignment='center', fontsize=10)

    save_to_directory(plt, temp_directory)

    plt.show()


def utc_to_seconds(utc_times):
    base_time = datetime.combine(datetime.today(), utc_times[0])  # Combine with today's date
    seconds = [(datetime.combine(datetime.today(), time) - base_time).total_seconds() for time in utc_times]
    return np.array(seconds)


def interpolate_ascension_data(times, altitudes_km, step=0.1):
    alt_min, alt_max = np.min(altitudes_km), np.max(altitudes_km)
    uniform_altitudes = np.arange(alt_min, alt_max, step)
    interpolate_func = interp1d(altitudes_km, times, kind='linear', bounds_error=False, fill_value="extrapolate")
    interpolated_times = interpolate_func(uniform_altitudes)
    return interpolated_times, uniform_altitudes


def plot_ascension_rate_troposphere(data, max_altitude, temp_directory):
    plt.figure(figsize=(10, 10))
    # Check if 'UTC' key exists and convert it, otherwise, use 'Time' directly
    if 'UTC' in data:
        times = utc_to_seconds(data['UTC'])  # Convert UTC times to seconds
    else:
        times = np.array(data['Time'])  # Use Time directly

    altitudes = np.array(data['Alt'])

    interpolated_times, uniform_altitudes = interpolate_ascension_data(times, altitudes)

    mask = uniform_altitudes <= max_altitude
    filtered_times = interpolated_times[mask]
    filtered_uniform_altitudes = uniform_altitudes[mask]

    time_derivative = np.gradient(filtered_times, filtered_uniform_altitudes)
    ascension_rates = 1000.0 / time_derivative

    coeffs = np.polyfit(filtered_uniform_altitudes, ascension_rates, 3)
    fit_uniform_altitudes_km = np.linspace(min(filtered_uniform_altitudes), max(filtered_uniform_altitudes), 500)
    fit_ascension_rates = np.polyval(coeffs, fit_uniform_altitudes_km)

    plt.plot(fit_ascension_rates, fit_uniform_altitudes_km, 'r-', label='Least Squares Regression Best Fit')
    plt.plot(ascension_rates, filtered_uniform_altitudes, linestyle='solid', color='k', label='Ascension Rate')
    plt.xlabel('Ascension Rate (m/s)')
    plt.ylabel('Altitude (km)')
    plt.title('Ascension Rate (Troposphere)')
    plt.grid(True)
    plt.legend()

    # Add description below the plot
    description = "Figure Description: This graph displays the ascension rate of the " \
                  "radiosonde, interpolated every 100 \n" \
                  "meters in the troposphere. The polynomial regression line of the ascension rate is plotted in red."
    plt.figtext(0.5, 0.03, description, wrap=True, horizontalalignment='center', fontsize=10)

    save_to_directory(plt, temp_directory)

    plt.show()


def plot_ascension_rate_stratosphere(data, min_altitude, temp_directory):
    plt.figure(figsize=(10, 10))
    # Check if 'UTC' key exists and convert it, otherwise, use 'Time' directly
    if 'UTC' in data:
        times = utc_to_seconds(data['UTC'])  # Convert UTC times to seconds
    else:
        times = np.array(data['Time'])  # Use Time directly

    altitudes = np.array(data['Alt'])

    # Use the interpolation function to get interpolated times and uniform altitudes
    interpolated_times, uniform_altitudes = interpolate_ascension_data(times, altitudes)

    # Filter the data up to the end_at_tropopause
    mask = uniform_altitudes >= min_altitude
    filtered_times = interpolated_times[mask]
    filtered_uniform_altitudes = uniform_altitudes[mask]

    # Calculate the derivative of time with respect to altitude (s/km), then convert to m/s
    time_derivative = np.gradient(filtered_times, filtered_uniform_altitudes)
    ascension_rates = 1000.0 / time_derivative  # Convert s/km to m/s

    # Fit a polynomial to the data and plot the curve of best fit
    coeffs = np.polyfit(filtered_uniform_altitudes, ascension_rates, 3)
    fit_uniform_altitudes_km = np.linspace(min(filtered_uniform_altitudes), max(filtered_uniform_altitudes), 500)
    fit_ascension_rates = np.polyval(coeffs, fit_uniform_altitudes_km)
    plt.plot(fit_ascension_rates, fit_uniform_altitudes_km, 'r-', label='Least Squares Regression Best Fit')
    # Plotting the ascension rate against altitude
    plt.plot(ascension_rates, filtered_uniform_altitudes, marker='', linestyle='solid', color='k',
             label='Ascension Rate')
    plt.xlabel('Ascension Rate (m/s)')
    plt.ylabel('Altitude (km)')
    plt.title('Ascension Rate (Stratosphere)')
    plt.grid(True)

    # Add description below the plot
    description = "Figure Description: This graph displays the ascension rate of the " \
                  "radiosonde, interpolated every 100 \n" \
                  "meters in the stratosphere. The polynomial regression line of the ascension rate is plotted in red."
    plt.figtext(0.5, 0.03, description, wrap=True, horizontalalignment='center', fontsize=10)

    save_to_directory(plt, temp_directory)

    plt.show()


def calculate_rate_of_change_grid(temperatures, altitudes):
    rate_of_change = np.gradient(temperatures, altitudes)
    return rate_of_change


def plot_wind_u_troposphere(data, max_altitude, temp_directory):
    plt.figure(figsize=(10, 10))
    speeds = np.array(data['Ws'])
    directions = np.array(data['Wd'])
    altitudes = np.array(data['Alt'])
    u = -speeds * np.sin(np.radians(directions))

    # Apply mask for data below the specified max_altitude
    mask = altitudes <= max_altitude
    filtered_u = u[mask]
    filtered_altitudes = altitudes[mask]

    # Fit a polynomial to act as the mean temperature curve
    coeffs = np.polyfit(filtered_altitudes, filtered_u, deg=3)
    y_curve = np.linspace(filtered_altitudes.min(), filtered_altitudes.max(), 500)
    x_curve = np.polyval(coeffs, y_curve)

    plt.plot(x_curve, y_curve, 'r-', label='Least Squares Regression Best Fit')

    plt.plot(filtered_u, filtered_altitudes, '', color='k', label="u Wind Component")

    plt.xlabel("u (m/s)")
    plt.ylabel('Altitude (km)')
    plt.title("u Wind Component vs. Altitude (Troposphere)")
    plt.legend()
    plt.grid(True)

    # Add description below the plot
    description = "Figure Description: This graph displays the u wind component with respect to altitude in \n" \
                  "the troposphere. The polynomial regression line of the u wind component is plotted in red."
    plt.figtext(0.5, 0.03, description, wrap=True, horizontalalignment='center', fontsize=10)

    save_to_directory(plt, temp_directory)

    plt.show()


def plot_wind_v_troposphere(data, max_altitude, temp_directory):
    plt.figure(figsize=(10, 10))
    speeds = np.array(data['Ws'])
    directions = np.array(data['Wd'])
    altitudes = np.array(data['Alt'])
    v = -speeds * np.cos(np.radians(directions))

    # Apply mask for data below the specified max_altitude
    mask = altitudes <= max_altitude
    filtered_v = v[mask]
    filtered_altitudes = altitudes[mask]

    # Fit a polynomial to act as the mean temperature curve
    coeffs = np.polyfit(filtered_altitudes, filtered_v, deg=3)
    y_curve = np.linspace(filtered_altitudes.min(), filtered_altitudes.max(), 500)
    x_curve = np.polyval(coeffs, y_curve)

    plt.plot(x_curve, y_curve, 'r-', label='Least Squares Regression Best Fit')

    plt.plot(filtered_v, filtered_altitudes, '', color='k', label="v Wind Component")

    plt.xlabel("v (m/s)")
    plt.ylabel('Altitude (km)')
    plt.title("v Wind Component vs. Altitude (Troposphere)")
    plt.legend()
    plt.grid(True)

    # Add description below the plot
    description = "Figure Description: This graph displays the v wind component with respect to altitude in \n" \
                  "the troposphere. The polynomial regression line of the v wind component is plotted in red."
    plt.figtext(0.5, 0.03, description, wrap=True, horizontalalignment='center', fontsize=10)

    save_to_directory(plt, temp_directory)

    plt.show()


def plot_wind_u_stratosphere(data, min_altitude, temp_directory):
    plt.figure(figsize=(10, 10))
    speeds = np.array(data['Ws'])
    directions = np.array(data['Wd'])
    altitudes = np.array(data['Alt'])
    u = -speeds * np.sin(np.radians(directions))

    # Apply mask for data below the specified max_altitude
    mask = altitudes >= min_altitude
    filtered_u = u[mask]
    filtered_altitudes = altitudes[mask]

    # Fit a polynomial to act as the mean temperature curve
    coeffs = np.polyfit(filtered_altitudes, filtered_u, deg=3)
    y_curve = np.linspace(filtered_altitudes.min(), filtered_altitudes.max(), 500)
    x_curve = np.polyval(coeffs, y_curve)

    plt.plot(x_curve, y_curve, 'r-', label='Least Squares Regression Best Fit')

    plt.plot(filtered_u, filtered_altitudes, '', color='k', label="u Wind Component")

    plt.xlabel("u (m/s)")
    plt.ylabel('Altitude (km)')
    plt.title("u Wind Component vs. Altitude (Stratosphere)")
    plt.legend()
    plt.grid(True)

    # Add description below the plot
    description = "Figure Description: This graph displays the u wind component with respect to altitude in \n" \
                  "the stratosphere. The polynomial regression line of the u wind component is plotted in red."
    plt.figtext(0.5, 0.03, description, wrap=True, horizontalalignment='center', fontsize=10)

    save_to_directory(plt, temp_directory)

    plt.show()


def plot_wind_v_stratosphere(data, min_altitude, temp_directory):
    plt.figure(figsize=(10, 10))
    speeds = np.array(data['Ws'])
    directions = np.array(data['Wd'])
    altitudes = np.array(data['Alt'])
    v = -speeds * np.cos(np.radians(directions))

    # Apply mask for data below the specified max_altitude
    mask = altitudes >= min_altitude
    filtered_v = v[mask]
    filtered_altitudes = altitudes[mask]

    # Fit a polynomial to act as the mean temperature curve
    coeffs = np.polyfit(filtered_altitudes, filtered_v, deg=3)
    y_curve = np.linspace(filtered_altitudes.min(), filtered_altitudes.max(), 500)
    x_curve = np.polyval(coeffs, y_curve)

    plt.plot(x_curve, y_curve, 'r-', label='Least Squares Regression Best Fit')

    plt.plot(filtered_v, filtered_altitudes, '', color='k', label="u Wind Component")

    plt.xlabel("v (m/s)")
    plt.ylabel('Altitude (km)')
    plt.title("v Wind Component vs. Altitude (Stratosphere)")
    plt.legend()
    plt.grid(True)

    # Add description below the plot
    description = "Figure Description: This graph displays the v wind component with respect to altitude in \n" \
                  "the stratosphere. The polynomial regression line of the v wind component is plotted in red."
    plt.figtext(0.5, 0.03, description, wrap=True, horizontalalignment='center', fontsize=10)

    save_to_directory(plt, temp_directory)

    plt.show()


def plot_u_perturbation_troposphere(data, max_altitude, temp_directory):
    plt.figure(figsize=(10, 10))
    speeds = np.array(data['Ws'])
    directions = np.array(data['Wd'])
    altitudes = np.array(data['Alt'])
    u = -speeds * np.sin(np.radians(directions))

    # Apply mask for data below the specified max_altitude
    mask = altitudes <= max_altitude
    filtered_u = u[mask]
    filtered_altitudes = altitudes[mask]

    # Fit a polynomial to act as the mean temperature curve
    coeffs = np.polyfit(filtered_altitudes, filtered_u, deg=3)
    mean_u_curve = np.polyval(coeffs, filtered_altitudes)

    # Calculate temperature perturbations by subtracting the mean temperature curve values
    u_perturbations = filtered_u - mean_u_curve

    # Plotting the raw temperature perturbation values
    plt.plot(u_perturbations, filtered_altitudes, color='k',
             label="u’ Wind Component",
             marker='',
             linestyle='solid')

    plt.xlabel("u’ Perturbation [m/s]")
    plt.ylabel("Altitude [km]")
    plt.title("u’ Wind Component Perturbation (Troposphere)")
    plt.legend()
    plt.grid(True)
    # Add description below the plot
    description = "Figure Description: This graph displays the u wind component perturbations from \n" \
                  "the mean u (u mean + u’) with respect to altitude in the troposphere. "
    plt.figtext(0.5, 0.03, description, wrap=True, horizontalalignment='center', fontsize=10)

    save_to_directory(plt, temp_directory)

    plt.show()


def plot_v_perturbation_troposphere(data, min_altitude, temp_directory):
    plt.figure(figsize=(10, 10))
    speeds = np.array(data['Ws'])
    directions = np.array(data['Wd'])
    altitudes = np.array(data['Alt'])
    v = -speeds * np.cos(np.radians(directions))

    # Apply mask for data below the specified max_altitude
    mask = altitudes >= min_altitude
    filtered_v = v[mask]
    filtered_altitudes = altitudes[mask]

    # Fit a polynomial to act as the mean temperature curve
    coeffs = np.polyfit(filtered_altitudes, filtered_v, deg=3)
    mean_v_curve = np.polyval(coeffs, filtered_altitudes)

    # Calculate temperature perturbations by subtracting the mean temperature curve values
    v_perturbations = filtered_v - mean_v_curve

    # Plotting the raw temperature perturbation values
    plt.plot(v_perturbations, filtered_altitudes, color='k',
             label="v’ Wind Component",
             marker='',
             linestyle='solid')

    plt.xlabel("v’ Perturbation [m/s]")
    plt.ylabel("Altitude [km]")
    plt.title("v’ Wind Component Perturbation (Troposphere)")
    plt.legend()
    plt.grid(True)

    # Add description below the plot
    description = "Figure Description: This graph displays the v wind component perturbations from \n" \
                  "the mean v (v mean + v’) with respect to altitude in the troposphere. "
    plt.figtext(0.5, 0.03, description, wrap=True, horizontalalignment='center', fontsize=10)

    save_to_directory(plt, temp_directory)

    plt.show()


def plot_u_perturbation_stratosphere(data, min_altitude, temp_directory):
    plt.figure(figsize=(10, 10))
    speeds = np.array(data['Ws'])
    directions = np.array(data['Wd'])
    altitudes = np.array(data['Alt'])
    u = -speeds * np.sin(np.radians(directions))

    # Apply mask for data below the specified max_altitude
    mask = altitudes >= min_altitude
    filtered_u = u[mask]
    filtered_altitudes = altitudes[mask]

    # Fit a polynomial to act as the mean temperature curve
    coeffs = np.polyfit(filtered_altitudes, filtered_u, deg=3)
    mean_u_curve = np.polyval(coeffs, filtered_altitudes)

    # Calculate temperature perturbations by subtracting the mean temperature curve values
    u_perturbations = filtered_u - mean_u_curve

    # Plotting the raw temperature perturbation values
    plt.plot(u_perturbations, filtered_altitudes, color='k',
             label="u’ Wind Component",
             marker='',
             linestyle='solid')

    plt.xlabel("u’ Perturbation [m/s]")
    plt.ylabel("Altitude [km]")
    plt.title("u’ Wind Component Perturbation (Stratosphere)")
    plt.legend()
    plt.grid(True)

    # Add description below the plot
    description = "Figure Description: This graph displays the u wind component perturbations from \n" \
                  "the mean u (u mean + u’) with respect to altitude in the stratosphere. "
    plt.figtext(0.5, 0.03, description, wrap=True, horizontalalignment='center', fontsize=10)

    save_to_directory(plt, temp_directory)

    plt.show()


def plot_v_perturbation_stratosphere(data, min_altitude, temp_directory):
    plt.figure(figsize=(10, 10))
    speeds = np.array(data['Ws'])
    directions = np.array(data['Wd'])
    altitudes = np.array(data['Alt'])
    v = -speeds * np.cos(np.radians(directions))

    # Apply mask for data below the specified max_altitude
    mask = altitudes >= min_altitude
    filtered_v = v[mask]
    filtered_altitudes = altitudes[mask]

    # Fit a polynomial to act as the mean temperature curve
    coeffs = np.polyfit(filtered_altitudes, filtered_v, deg=3)
    mean_v_curve = np.polyval(coeffs, filtered_altitudes)

    # Calculate temperature perturbations by subtracting the mean temperature curve values
    v_perturbations = filtered_v - mean_v_curve

    # Plotting the raw temperature perturbation values
    plt.plot(v_perturbations, filtered_altitudes, color='k',
             label="v’ Wind Component",
             marker='',
             linestyle='solid')

    plt.xlabel("v’ Perturbation [m/s]")
    plt.ylabel("Altitude [km]")
    plt.title("v’ Wind Component Perturbation (Stratosphere)")
    plt.legend()
    plt.grid(True)

    # Add description below the plot
    description = "Figure Description: This graph displays the v wind component perturbations from \n" \
                  "the mean v (v mean + v’) with respect to altitude in the stratosphere. "
    plt.figtext(0.5, 0.03, description, wrap=True, horizontalalignment='center', fontsize=10)

    save_to_directory(plt, temp_directory)

    plt.show()


def find_tropopause_with_gradient(data):
    temperatures = np.array(data['T'])
    altitudes = np.array(data['Alt'])
    # Interpolate temperature data over a uniform grid
    interpolated_temperatures, uniform_altitudes = interpolate_temperature_data(temperatures, altitudes)

    # Filter to consider only altitudes above 5 kilometers
    mask_above_5km = uniform_altitudes > 5.5
    filtered_temperatures = interpolated_temperatures[mask_above_5km]
    filtered_altitudes = uniform_altitudes[mask_above_5km]

    # Calculate lapse rate for the filtered data
    lapse_rate = -calculate_rate_of_change_grid(filtered_temperatures, filtered_altitudes)

    # Find the first index where lapse rate is less than 1.5°C per km in filtered data
    tropopause_index = np.where(lapse_rate < 1.5)[0][0]

    return filtered_altitudes[tropopause_index]
