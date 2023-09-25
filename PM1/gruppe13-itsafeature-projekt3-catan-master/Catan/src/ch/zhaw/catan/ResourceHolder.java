package ch.zhaw.catan;

import ch.zhaw.catan.Config.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This class manages the Resource stock for every Player and the Bank
 *
 * For example, calling the method {@link ResourceHolder#addResource(Resource, int)}}
 * will add an amount of a Resource to the stock
 *
 * @author Badr Outiti
 */
public class ResourceHolder {
    private HashMap<Resource, Integer> resources;

    /**
     * Get the current Resource stock of a specific Resource
     *
     * @param resource the Resource that is targeted
     * @return The amount of the specific Resource in the stock
     */
    public int getResourceStock(Resource resource) {
        return resources.get(resource);
    }

    /**
     * Add a specific amount of Resources to the stock
     *
     * @param resource         The specific Resource that is targeted
     * @param amountOfResource The amount of Resources that will be added
     * @throws IllegalArgumentException if amountOfResource isn't positive or
     *                                  the Resource stock is null
     */
    public void addResource(Resource resource, int amountOfResource) {
        if (resources != null && amountOfResource >= 0) {
            resources.merge(resource, amountOfResource, Integer::sum);
        } else {
            throw new IllegalArgumentException("This action is not executable");
        }
    }

    /**
     * Remove a specific amount of Resources from the stock
     *
     * @param resource         The specific Resource that is targeted
     * @param amountOfResource The number of Resources that will be removed
     * @throws IllegalArgumentException if amountOfResource isn't positive or if there are
     *                                  less Resources in the stock than the amount of Resources that are getting subtracted
     */
    public void removeResource(Resource resource, int amountOfResource) {
        if (resources.get(resource) >= amountOfResource && amountOfResource >= 0) {
            amountOfResource = amountOfResource * -1;
            resources.merge(resource, amountOfResource, Integer::sum);

        } else {
            throw new IllegalArgumentException("This action is not executable");
        }
    }

    /**
     * Set the Resources of the Stock
     *
     * @param resourceStock The amount of Resource and the Resource that will be set as the stock
     */
    protected void setResources(HashMap<Resource, Integer> resourceStock) {
        this.resources = resourceStock;
    }

    /**
     * Add Resources to the stock, with a Map as parameter
     *
     * @param resourcesToAdd The amount of the Resource that will be added to the Stock
     */
    public void addResourceWithMap(Map<Resource, Long> resourcesToAdd) {
        for (Map.Entry<Resource, Long> resource : resourcesToAdd.entrySet()) {
            addResource(resource.getKey(), resource.getValue().intValue());
        }
    }

    /**
     * Remove a specific amount of Resources from the stock with a map as parameter
     *
     * @param resourcesToSubtract The amount of Resources that will be removed from the stock
     */

    public void removeResourceWithMap(Map<Resource, Long> resourcesToSubtract) {
        for (Map.Entry<Resource, Long> resource : resourcesToSubtract.entrySet()) {
            removeResource(resource.getKey(), resource.getValue().intValue());
        }
    }

    /**
     * Get how many resources 1 Player has added together
     *
     * @return The amount of Resources that are currently in the Stock
     */
    public int getAllCardsInHand() {
        int resourceStock = 0;
        for (int i = 0; i < Resource.values().length; i++) {
            Config.Resource[] values = Config.Resource.values();
            getResourceStock(values[i]);
            resourceStock += getResourceStock(values[i]);
        }
        return resourceStock;
    }

    /**
     * Check if the current Resource Holder has enough of a specific Resource
     *
     * @param resource         The Resource that is targeted
     * @param amountOfResource The amount of the specific Resource
     * @return True if the Holder has enough Resources and false if he hasn't
     */
    public boolean hasEnoughResource(Resource resource, int amountOfResource) {
        boolean hasEnough = false;
        if (getResourceStock(resource) >= amountOfResource) {
            hasEnough = true;
        }
        return hasEnough;
    }

    /**
     * Deletes a specific amount of resources randomly
     *
     * @param countToDelete The amount of Resources that will be deleted
     * @return A map with the amount of Resources that got deleted
     */
    public HashMap<Resource, Long> deleteResources(int countToDelete) {
        //Create a flat list from the Hashmap
        //This ensures true randomness
        ArrayList<Resource> resourceArrayList = generateFlatResourceList();
        HashMap<Resource, Long> deletedResources = new HashMap<>();

        Random randomGenerator = new Random();
        for (int resourceCounter = 0; resourceCounter < countToDelete; resourceCounter++) {
            int randomNumber = randomGenerator.nextInt(resourceArrayList.size()) + 1;
            removeResource(resourceArrayList.get(randomNumber - 1), 1);
            deletedResources.merge(resourceArrayList.get(randomNumber - 1), (long) 1, Long::sum);
            resourceArrayList.remove(randomNumber - 1);
        }
        return deletedResources;
    }

    /**
     * This method takes one random resource and removes it from the ResourceHolder
     *
     * @return the Resource which was stolen
     */
    public Resource stealAResource() {
        ArrayList<Resource> resourceArrayList = generateFlatResourceList();
        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(resourceArrayList.size()) + 1;

        Resource resourceToBeStolen = resourceArrayList.get(randomNumber - 1);
        removeResource(resourceToBeStolen, 1);

        return resourceToBeStolen;
    }

    /**
     * This method generates an Arraylist with all resources
     * This map has a flat structure to ensure true randomness when picking a Resource
     *
     * @return a flat ArrayList with all Resources
     */
    private ArrayList<Resource> generateFlatResourceList() {
        ArrayList<Resource> resourceArrayList = new ArrayList<>();

        for (Resource resource : resources.keySet()) {
            int resourceCount = resources.get(resource);

            for (int i = 0; i < resourceCount; i++) {
                resourceArrayList.add(resource);
            }
        }
        return resourceArrayList;
    }
}

