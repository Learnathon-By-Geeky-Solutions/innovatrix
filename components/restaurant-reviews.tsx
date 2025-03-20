/* eslint-disable @next/next/no-img-element */
"use client"

import { useState } from "react"
import { Button } from "@/components/ui/button"
import { Textarea } from "@/components/ui/textarea"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Star } from 'lucide-react'

interface RestaurantReviewsProps {
  restaurantId: number
}

// eslint-disable-next-line @typescript-eslint/no-unused-vars
export function RestaurantReviews({ restaurantId }: RestaurantReviewsProps) {
  const [rating, setRating] = useState(0)
  const [review, setReview] = useState("")

  const reviews = [
    {
      id: 1,
      user: {
        name: "Sarah Ahmed",
        avatar: "/placeholder.svg?height=40&width=40",
      },
      rating: 5,
      date: "2 days ago",
      content: "Amazing food and great service! The Kacchi Biryani was exceptional.",
      images: ["/placeholder.svg?height=100&width=100"],
    },
    {
      id: 2,
      user: {
        name: "Karim Rahman",
        avatar: "/placeholder.svg?height=40&width=40",
      },
      rating: 4,
      date: "1 week ago",
      content: "Good food but the waiting time was a bit long. Would still recommend!",
      images: [],
    },
  ]

  return (
    <div className="space-y-6">
      <div className="space-y-4">
        <h3 className="font-semibold">Write a Review</h3>
        <div className="flex gap-2">
          {[1, 2, 3, 4, 5].map((star) => (
            <Button
              key={star}
              variant="ghost"
              size="icon"
              onClick={() => setRating(star)}
              className={star <= rating ? "text-yellow-400" : ""}
            >
              <Star className={`h-6 w-6 ${star <= rating ? "fill-current" : ""}`} />
            </Button>
          ))}
        </div>
        <Textarea
          placeholder="Share your experience..."
          value={review}
          onChange={(e) => setReview(e.target.value)}
        />
        <Button>Submit Review</Button>
      </div>

      <div className="space-y-6">
        {reviews.map((review) => (
          <div key={review.id} className="space-y-2">
            <div className="flex items-center gap-2">
              <Avatar>
                <AvatarImage src={review.user.avatar} />
                <AvatarFallback>{review.user.name[0]}</AvatarFallback>
              </Avatar>
              <div>
                <div className="font-semibold">{review.user.name}</div>
                <div className="text-sm text-gray-500">{review.date}</div>
              </div>
            </div>
            <div className="flex gap-1">
              {Array.from({ length: review.rating }).map((_, i) => (
                <Star
                  key={i}
                  className="h-4 w-4 fill-yellow-400 text-yellow-400"
                />
              ))}
            </div>
            <p className="text-gray-600">{review.content}</p>
            {review.images.length > 0 && (
              <div className="flex gap-2 mt-2">
                {review.images.map((image, i) => (
                  <img
                    key={i}
                    src={image}
                    alt={`Review ${i + 1}`}
                    className="w-20 h-20 object-cover rounded-lg"
                  />
                ))}
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  )
}

