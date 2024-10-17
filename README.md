**Duca Andrei Rares**
*321CA*

The project simulates an application similar in functionality to Spotify.

# Code Details:

## Admin
  * The Singleton Pattern is used for this class, with the getInstance method.
  * The addSongs/addPodcasts methods add new items from the Artist/Host to the library.
  * The getUser/Artist/Host methods return the user based on the given name, or null if the user does not exist in the library.
  * The getTop5Albums method sorts albums based on the total number of likes and then lexicographically, returning the top 5 albums.
  * The getOnlineUsers method iterates through the user array to check who is online.
  * The addUser method checks if there is no other user with the same name, and then adds the corresponding user. It also includes logic to check if the user can be deleted.
  * The isUsingUser method is used to check if a user can be deleted or if they have items being used by other users.
  * The deleteUser method deletes the desired user as long as they are not interacting with anyone.
  * The removePlaylists/Album/Podcasts method removes a specified object from the library.

## Player
  * stopUsingBySearch: when performing a search, the stop method is called, which removes the current interactions in the player's user.
  * The check...Using methods set the interaction between the user and the Artist/Host based on the player.

## Album : class created for entities generated by an Artist.
  * The getTotalLikes method returns the total number of likes for an album by summing up the total likes of the songs in the album.

## Host
  * addPodcast: checks if it can be added based on the name and its episodes. Based on the result, it either adds the podcast to the library or not.
  * isUsingPodcast: checks if a podcast is being interacted with by a user to allow it to be deleted later.
  * addUsingUsers: adds a new user to the interaction list (when adding a new type user).
  * isUsed: checks if any user is interacting with the current Host.

## Artist
  * addAlbum: checks if the album can be added based on the name and the songs in it, and based on the result, either adds it to the library or not.
  * getTotalLikes: returns all the likes of an artist by summing the likes of each of their albums.

## User
  * startUsing...BySelect: depending on what is selected, an interaction is triggered between the artist/host's page and the user who made the selection.
  * printHome and printLikedContentPage: handle the display of the content on the two user pages.
  * changePage: checks if the new page exists and moves the user to it.
