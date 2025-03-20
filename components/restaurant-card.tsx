/* eslint-disable @next/next/no-img-element */
"use client"

import { useState } from "react"
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { Card, CardContent, CardHeader } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Star, MapPin, Clock, Phone, ExternalLink, Share2 } from 'lucide-react'
import { RestaurantReviews } from "./restaurant-reviews"

interface RestaurantCardProps {
  id?: number
  name: string
  image: string
  rating: number
  reviews: number
  cuisine: string
  priceRange: string
  location: string
  distance?: string
  isOpen?: boolean
  popular?: string[]
  categories?: string[]
}

export function RestaurantCard({
  id = 0,
  name,
  image,
  rating,
  reviews,
  cuisine,
  priceRange,
  location,
  distance = "1 km",
  isOpen = true,
  popular = [],
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  categories = [],
}: RestaurantCardProps) {
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [isLiked, setIsLiked] = useState(false)

  return (
    <Dialog>
      <DialogTrigger asChild>
        <Card className="overflow-hidden group cursor-pointer">
          <CardContent className="p-0">
            <div className="relative">
              <img
                src={image}
                alt={name}
                className="w-full h-48 object-cover transition-transform group-hover:scale-105"
              />
              <Badge className="absolute top-4 right-4 bg-white/90 text-black">
                {priceRange}
              </Badge>
              <Badge 
                className={`absolute top-4 left-4 ${
                  isOpen ? "bg-green-500" : "bg-red-500"
                }`}
              >
                {isOpen ? "Open" : "Closed"}
              </Badge>
            </div>
            <div className="p-4">
              <h3 className="font-semibold text-lg mb-2">{name}</h3>
              <div className="flex items-center gap-2 text-sm text-gray-600 mb-2">
                <Star className="h-4 w-4 fill-yellow-400 text-yellow-400" />
                <span>{rating}</span>
                <span>({reviews} reviews)</span>
              </div>
              <div className="flex items-center justify-between">
                <Badge variant="secondary">{cuisine}</Badge>
                <div className="flex items-center gap-1 text-sm text-gray-600">
                  <MapPin className="h-4 w-4" />
                  {distance}
                </div>
              </div>
            </div>
          </CardContent>
        </Card>
      </DialogTrigger>

      <DialogContent className="max-w-3xl">
        <DialogHeader>
          <DialogTitle>{name}</DialogTitle>
        </DialogHeader>
        <Tabs defaultValue="info">
          <TabsList className="grid w-full grid-cols-3">
            <TabsTrigger value="info">Information</TabsTrigger>
            <TabsTrigger value="menu">Menu</TabsTrigger>
            <TabsTrigger value="reviews">Reviews</TabsTrigger>
          </TabsList>
          <TabsContent value="info" className="space-y-4">
            <div className="aspect-video relative rounded-lg overflow-hidden">
              <img
                src={image}
                alt={name}
                className="w-full h-full object-cover"
              />
            </div>
            <div className="grid gap-4">
              <div className="flex items-center justify-between">
                <div className="flex items-center gap-2">
                  <Star className="h-5 w-5 fill-yellow-400 text-yellow-400" />
                  <span className="font-semibold">{rating}</span>
                  <span className="text-gray-600">({reviews} reviews)</span>
                </div>
                <Badge variant="outline" className="text-green-600">
                  {isOpen ? "Open Now" : "Closed"}
                </Badge>
              </div>
              <div className="grid gap-2 text-sm">
                <div className="flex items-center gap-2">
                  <MapPin className="h-4 w-4" />
                  <span>{location} • {distance}</span>
                </div>
                <div className="flex items-center gap-2">
                  <Clock className="h-4 w-4" />
                  <span>11:00 AM - 11:00 PM</span>
                </div>
                <div className="flex items-center gap-2">
                  <Phone className="h-4 w-4" />
                  <span>+880 1234-567890</span>
                </div>
              </div>
              <div>
                <h4 className="font-semibold mb-2">Popular Dishes</h4>
                <div className="flex flex-wrap gap-2">
                  {popular.map((dish) => (
                    <Badge key={dish} variant="secondary">
                      {dish}
                    </Badge>
                  ))}
                </div>
              </div>
              <div className="flex gap-2">
                <Button className="flex-1">
                  Book a Table
                </Button>
                <Button variant="outline" size="icon">
                  <Share2 className="h-4 w-4" />
                </Button>
                <Button variant="outline" size="icon">
                  <ExternalLink className="h-4 w-4" />
                </Button>
              </div>
            </div>
          </TabsContent>
          <TabsContent value="menu">
            <div className="space-y-4">
              <div className="grid gap-4">
                {["Appetizers", "Main Course", "Desserts"].map((category) => (
                  <div key={category}>
                    <h3 className="font-semibold mb-3">{category}</h3>
                    <div className="space-y-3">
                      {[1, 2, 3].map((item) => (
                        <div
                          key={item}
                          className="flex justify-between items-start pb-3 border-b"
                        >
                          <div>
                            <h4 className="font-medium">Dish Name {item}</h4>
                            <p className="text-sm text-gray-600">
                              Description of the dish goes here
                            </p>
                          </div>
                          <span className="font-semibold">৳250</span>
                        </div>
                      ))}
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </TabsContent>
          <TabsContent value="reviews">
            <RestaurantReviews restaurantId={id} />
          </TabsContent>
        </Tabs>
      </DialogContent>
    </Dialog>
  )
}

